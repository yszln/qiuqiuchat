package com.yszln.qiuqiu.api

import com.yszln.lib.network.RetrofitClient

object Api {
    const val BASE_API = "http://huwei.iask.in";
    const val SOCKET_API = "ws://huwei.iask.in/websocket/";

    val mApiServer by lazy { RetrofitClient.createApi(BASE_API, ApiServer::class.java) }



}