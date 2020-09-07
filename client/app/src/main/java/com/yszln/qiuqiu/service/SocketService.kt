package com.yszln.qiuqiu.service

import android.app.Service
import android.content.Intent
import android.os.*
import android.util.Log
import com.yszln.lib.network.ApiFactory
import com.yszln.qiuqiu.api.Api
import com.yszln.qiuqiu.db.UserUtils
import okhttp3.*
import okio.ByteString

class SocketService : Service() {


    // 发送心跳包
    private val mHandler: Handler = MyHandler()

    // 每隔2秒发送一次心跳包，检测连接没有断开
    private val HEART_BEAT_RATE = 5 * 1000.toLong()
    private var mSocket: WebSocket

    init {
        val socketRequest = Request
            .Builder()
            .url(Api.SOCKET_API + UserUtils.getToken())
            .build()

        mSocket = ApiFactory.mOkHttpClient.newWebSocket(socketRequest, MyListener())
        mSocket.request()

        //启动心跳检测
        mHandler.sendEmptyMessageDelayed(0, HEART_BEAT_RATE)
    }

    override fun onBind(p0: Intent?) = MyBinder()


    open class MyListener : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: Response) {
            super.onOpen(webSocket, response)
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            super.onClosed(webSocket, code, reason)
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            super.onClosing(webSocket, code, reason)
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            super.onFailure(webSocket, t, response)
            Log.e("Socket", "onFailure:${t.message}")
        }

        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
            super.onMessage(webSocket, bytes)
            Log.e("Socket", "onMessage:${bytes}")
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            super.onMessage(webSocket, text)
            Log.e("Socket", "onMessage:${text}")
        }
    }

    inner class MyHandler : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            mSocket.send("")
            sendEmptyMessageDelayed(0, HEART_BEAT_RATE)
        }
    }

    inner class MyBinder : Binder() {
        fun send(message: String) {
            mSocket.send(message)
        }

        fun send(bytes: ByteString) {
            mSocket.send(bytes)
        }
    }


}