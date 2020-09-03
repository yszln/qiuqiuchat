package com.yszln.qiuqiu

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
   lateinit var mSocketBind:SocketService.MyBinder


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent = Intent(this, SocketService::class.java)
        bindService(intent,MyConn(), Context.BIND_AUTO_CREATE)

        textview.setOnClickListener {
            mSocketBind.send(content.text.toString())
        }
    }

    inner class MyConn:ServiceConnection{
        override fun onServiceDisconnected(p0: ComponentName?) {

        }

        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            mSocketBind=  (p1 as SocketService.MyBinder)
        }

    }
}


