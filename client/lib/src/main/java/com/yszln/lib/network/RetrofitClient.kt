package com.yszln.lib.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    /**
     * 创建api接口
     */
    fun <T> createApi(baseUrl: String, clazz: Class<T>): T {
        return Retrofit.Builder()
            //baseUrl
            .baseUrl(baseUrl)
            //okHttp客户端
            .client(ApiFactory.mOkHttpClient)
            //协程适配器
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            //gson转换工厂
            .addConverterFactory(GsonConverterFactory.create(ApiFactory.getGson()))
            .build()
            //创建api
            .create(clazz)
    }
}