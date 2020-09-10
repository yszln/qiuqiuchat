package com.yszln.qiuqiu

import com.yszln.lib.app.BaseApplication
import kotlin.properties.Delegates

class MyApp : BaseApplication() {

    companion object {
        var instance: MyApp by Delegates.notNull()

    }

    override fun onCreate() {
        super.onCreate()
        instance = this


    }

}