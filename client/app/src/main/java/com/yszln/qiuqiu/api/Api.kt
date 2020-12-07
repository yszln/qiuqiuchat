package com.yszln.qiuqiu.api

import com.yszln.lib.network.RetrofitClient

object Api {
    const val BASE_API = "http://121.4.67.101:8080/";
    const val SOCKET_API = "ws://121.4.67.101:8080/websocket/";

    val mApiServer by lazy { RetrofitClient.createApi(BASE_API, ApiServer::class.java) }



}