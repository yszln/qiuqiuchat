package com.yszln.qiuqiu.api

import com.yszln.lib.network.RetrofitClient

object Api {
    const val BASE_API = "http://192.168.123.37:8080";
    const val SOCKET_API = "ws://192.168.123.37:8080/websocket/";
    const val SOCKET_API2 = "ws://192.168.123.37:8086/socketServer/";

    val mApiServer by lazy { RetrofitClient.createApi(BASE_API, ApiServer::class.java) }



}