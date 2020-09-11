package com.yszln.qiuqiu.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Handler
import android.os.Looper
import android.os.Message
import com.neovisionaries.ws.client.*
import com.yszln.lib.bean.BaseBean
import com.yszln.lib.bus.LiveDataBus
import com.yszln.lib.network.ApiExceptionHandler
import com.yszln.lib.utils.LogUtil
import com.yszln.lib.utils.jsonFormat
import com.yszln.qiuqiu.api.Api
import com.yszln.qiuqiu.db.CacheDataBase
import com.yszln.qiuqiu.db.UserUtils
import com.yszln.qiuqiu.db.table.TbMessage
import com.yszln.qiuqiu.ui.chat.model.ChatEnum
import com.yszln.qiuqiu.utils.Constant
import okio.ByteString

class WebSocketService : Service() {


    // 发送心跳包
    private val mHandler: Handler = MyHandler()

    // 每隔2秒发送一次心跳包，检测连接没有断开
    private val HEART_BEAT_RATE = 10000L

    /**
     * socket连接
     */
    private lateinit var mSocket: WebSocket


    private val MESSAGE_HEART = 0x001

    /**
     * 连接状态
     */
    private var isConn = false;


    override fun onCreate() {
        super.onCreate()
        connectionSocket()
    }

    /**
     * 连接socket
     */
    private fun connectionSocket() {
        mSocket = WebSocketFactory()
            .createSocket(Api.SOCKET_API + UserUtils.getToken(), 30)
            .setFrameQueueSize(5)//设置帧队列最大值为5
            .setMissingCloseFrameAllowed(false)//设置不允许服务端关闭连接却未发送关闭帧
            .addListener(mWebSocketListener)//添加回调监听
            .connectAsynchronously()//异步连接

    }

    override fun onBind(intent: Intent?) = MyBinder()


    inner class MyHandler : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                MESSAGE_HEART -> {
                    //心跳检测
                    heartBeatRate()
                }
            }

        }


    }

    /**
     * 心跳检测
     */
    private fun heartBeatRate() {
        if (isConn) {
            //连接正常，进行心跳检测
            val send = mSocket.sendText("")
            LogUtil.e("SocketListener", "heartBeatRate:${send.isOpen}")
            mHandler.sendEmptyMessageDelayed(MESSAGE_HEART, HEART_BEAT_RATE)
        } else {
            //连接断开，重新连接
            mHandler.removeCallbacksAndMessages(null)
            mHandler.postDelayed({
                connectionSocket()
            }, HEART_BEAT_RATE)
        }

    }

    val mWebSocketListener = object : WebSocketAdapter() {
        override fun onDisconnected(
            websocket: WebSocket?,
            serverCloseFrame: WebSocketFrame?,
            clientCloseFrame: WebSocketFrame?,
            closedByServer: Boolean
        ) {
            super.onDisconnected(websocket, serverCloseFrame, clientCloseFrame, closedByServer)
            //连接断开
            LogUtil.e("连接断开，开始重连")
            disConnection()
        }

        @Throws(Exception::class)
        override fun onTextMessage(websocket: WebSocket, text: String) {
            super.onTextMessage(websocket, text)
            LogUtil.e("收到消息：$text")
            //接收到服务器数据
            handlerMessage(websocket, text)


        }

        override fun onTextMessage(websocket: WebSocket?, data: ByteArray?) {
            super.onTextMessage(websocket, data)
        }

        override fun onMessageError(
            websocket: WebSocket?,
            cause: WebSocketException?,
            frames: MutableList<WebSocketFrame>?
        ) {
            super.onMessageError(websocket, cause, frames)
            LogUtil.e("onMessageError")
        }

        @Throws(Exception::class)
        override fun onConnected(websocket: WebSocket, headers: Map<String, List<String>>) {
            super.onConnected(websocket, headers)
            connectionSuccess(websocket)
            LogUtil.e("连接成功")
        }

        @Throws(Exception::class)
        override fun onConnectError(websocket: WebSocket, exception: WebSocketException) {
            super.onConnectError(websocket, exception)
            LogUtil.e("连接错误，开始重连")
            disConnection();
        }

    }

    /**
     * 处理message
     */
    private fun handlerMessage(websocket: WebSocket, text: String) {
        text.jsonFormat(TbMessage::class.java).apply {
            itemType = ChatEnum.OTHER.value
            CacheDataBase.instance.messageDao().insert(this)
            LiveDataBus.post(Constant.SEND_MESSAGE, this)
        }


    }


    /**
     * 连接成功
     */
    private fun connectionSuccess(webSocket: WebSocket) {
        mSocket = webSocket
        isConn = true
        //启动心跳检测
        mHandler.removeCallbacksAndMessages(null)
        heartBeatRate()
    }

    /**
     * 连接断开
     */
    private fun disConnection() {
        isConn = false
        mHandler.removeCallbacksAndMessages(null)
        heartBeatRate();
    }


    /**
     * 暴露方法
     */
    inner class MyBinder : Binder() {
        fun send(message: String) {
            mSocket.sendText(message)
        }

        fun send(byte: ByteString) {
            mSocket.sendBinary(byte.toByteArray())
        }

        fun send(bytes: ByteArray) {
            mSocket.sendBinary(bytes)
        }
    }


}


