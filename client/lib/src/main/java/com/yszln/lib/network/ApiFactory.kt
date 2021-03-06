package com.yszln.lib.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.yszln.lib.app.BaseApplication
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

/**
 * 接口工厂
 */
object ApiFactory {
    /**
     * 日志拦截器
     */
    private val mLoggingInterceptor: LogInterceptor by lazy { LogInterceptor.Builder().build() }

    /**
     * OKHttp客户端
     */
    val mOkHttpClient: OkHttpClient by lazy { newClient() }

    var mLoginCookie: CookieJarImpl = CookieJarImpl()

    /**
     * 创建OKHttp客户端
     */
    public fun newClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            //连接超时时间
            connectTimeout(15, TimeUnit.SECONDS)
            //读取超时时间
            readTimeout(15, TimeUnit.SECONDS)
            //写入超时时间
            writeTimeout(60, TimeUnit.SECONDS)
                .pingInterval(10000, TimeUnit.SECONDS)
            //debug模式添加日志拦截器
            if (BaseApplication.isDebug) {
                addInterceptor(mLoggingInterceptor)
            }
            addInterceptor(
                Class.forName("com.yszln.qiuqiu.api.HeadInterceptor").newInstance() as Interceptor
            )

//            cookieJar(mLoginCookie)
        }.build();
    }


    fun getGson(): Gson {
        return GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .serializeNulls()
            .create()
    }

}