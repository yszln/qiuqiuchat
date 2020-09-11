package com.yszln.qiuqiu.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Handler
import android.os.Looper
import android.os.Message
import com.yszln.lib.network.ApiFactory
import com.yszln.lib.utils.LogUtil
import com.yszln.lib.utils.toast
import com.yszln.qiuqiu.api.Api
import com.yszln.qiuqiu.db.UserUtils
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okio.ByteString

@Deprecated("鬼晓得除了啥问题")
class OkSocketService : Service() {


    // 发送心跳包
    private val mHandler: Handler = MyHandler()

    // 每隔2秒发送一次心跳包，检测连接没有断开
    private val HEART_BEAT_RATE = 10000L

    /**
     * socket连接
     */
    private lateinit var mSocket: WebSocket

    /**
     * socket请求
     */
    private var mSocketRequest: Request = Request
        .Builder()
        .url(Api.SOCKET_API + UserUtils.getToken())
        .build()

    private val MESSAGE_HEART = 0x001

    /**
     * 连接状态
     */
    private var isConn = false;

    /**
     * socket回调
     */
    private val mReceiveListener by lazy { createListener() }


    override fun onCreate() {
        super.onCreate()
        connectionSocket()
    }

    /**
     * 连接socket
     */
    private fun connectionSocket() {

        mSocket = ApiFactory.mOkHttpClient.newWebSocket(mSocketRequest, mReceiveListener)
        mSocket.request()

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
            val send = mSocket.send("")
            LogUtil.e("SocketListener", "heartBeatRate:${send}")
            mHandler.sendEmptyMessageDelayed(MESSAGE_HEART, HEART_BEAT_RATE)
        } else {
            //连接断开，重新连接
            mHandler.removeCallbacksAndMessages(null)
            connectionSocket()
        }

    }

    private fun createListener() = object : IReceiveListener() {
        override fun onDisconnection(
            webSocket: WebSocket,
            code: Int,
            reason: String,
            t: Throwable
        ) {
            //连接断开
            disConnection()
        }

        /**
         * 连接成功
         */
        override fun onOpen(webSocket: WebSocket, response: Response) {
            super.onOpen(webSocket, response)
            LogUtil.e("SocketListener", "onOpen")
            connectionSuccess(webSocket)
        }

        /**
         * 收到消息
         */
        override fun onMessage(webSocket: WebSocket, text: String) {
            super.onMessage(webSocket, text)
            text.toast()
            LogUtil.e("SocketListener", "onMessage:${text}")
        }

        /**
         * 收到消息
         */
        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
            super.onMessage(webSocket, bytes)
            LogUtil.e("SocketListener", "onMessage:${bytes}")
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
            mSocket.send(message)
        }

        fun send(bytes: ByteString) {
            mSocket.send(bytes)
        }
    }


}