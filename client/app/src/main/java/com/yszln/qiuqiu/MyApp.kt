package com.yszln.qiuqiu

import android.app.Application
import android.content.Intent

class MyApp : Application(){
    override fun onCreate() {
        super.onCreate()
        val intent = Intent(this, SocketService::class.java)
        startService(intent)
    }
}