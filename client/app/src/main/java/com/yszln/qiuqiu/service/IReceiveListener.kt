package com.yszln.qiuqiu.service

import com.yszln.lib.utils.LogUtil
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

abstract class IReceiveListener : WebSocketListener() {

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosed(webSocket, code, reason)
        LogUtil.e("SocketListener", "onClosed:${code},${reason}")
        onDisconnection(webSocket,code,reason,RuntimeException("onClose"))
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosing(webSocket, code, reason)
        LogUtil.e("SocketListener", "onClosing:${code},${reason}")
        onDisconnection(webSocket,code,reason,RuntimeException("onClosing"))
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        super.onFailure(webSocket, t, response)
        LogUtil.e("SocketListener", "onFailure:${t.message}")
        onDisconnection(webSocket,500,"onFailure",t)
    }
    abstract fun onDisconnection(webSocket: WebSocket,code: Int,reason: String,t: Throwable)


}