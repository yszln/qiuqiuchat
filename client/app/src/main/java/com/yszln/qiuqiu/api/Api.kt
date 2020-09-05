package com.yszln.qiuqiu.api

import com.yszln.lib.network.RetrofitClient

object Api {
    const val BASE_API = "http://192.168.123.129";

    val mApiServer by lazy { RetrofitClient.createApi(BASE_API, ApiServer::class.java) }
}