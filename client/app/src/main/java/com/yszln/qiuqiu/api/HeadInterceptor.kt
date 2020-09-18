package com.yszln.qiuqiu.api

import com.yszln.qiuqiu.db.UserUtils
import okhttp3.Interceptor
import okhttp3.Response

class HeadInterceptor : Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            chain
                .request()
                .newBuilder()
                .header("token", UserUtils.getToken())
                .build()
        )
    }


}
