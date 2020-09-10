package com.yszln.lib.network

import com.yszln.lib.utils.SPUtils
import okhttp3.Interceptor
import okhttp3.Response

class HeadInterceptor : Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            chain
                .request()
                .newBuilder()
                .header("token", getToken())
                .build()
        )
    }

    fun getToken(): String {
        return SPUtils.get("TOKEN") ?: ""
    }
}
