package com.yszln.qiuqiu

import com.yszln.lib.app.BaseApplication
import com.yszln.lib.network.ApiExceptionHandler
import com.yszln.qiuqiu.db.UserUtils
import kotlin.properties.Delegates

class MyApp : BaseApplication() {

    companion object {
        var instance: MyApp by Delegates.notNull()

    }

    override fun onCreate() {
        super.onCreate()
        instance = this

       setApiExceptionHandle()

    }

    private fun setApiExceptionHandle() {
        ApiExceptionHandler.mApiExceptionListener=object : ApiExceptionHandler.ApiExceptionListener{
            override fun onError(code: Int, message: String) {
                when (code) {
                    501 -> {
                        //登录过期，执行退出登录操作
                        UserUtils.loginOut()
                    }
                }
            }
        }
    }

}