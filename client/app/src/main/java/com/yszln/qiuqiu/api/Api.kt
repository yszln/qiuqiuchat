package com.yszln.qiuqiu.api

import com.yszln.lib.network.RetrofitClient

object Api {
    const val BASE_API = "http://huwei.iask.in";
//    const val SOCKET_API = "ws://huwei.iask.in/websocket/";
    const val SOCKET_API = "ws://192.168.123.37:8080/websocket/";

    val mApiServer by lazy { RetrofitClient.createApi(BASE_API, ApiServer::class.java) }



}