package com.yszln.qiuqiu.base

import android.content.ComponentName
import android.content.Context
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import com.yszln.lib.activity.BaseLoadMoreActivity
import com.yszln.qiuqiu.service.SocketService
import com.yszln.qiuqiu.ui.chat.viewmodel.ChatViewModel
import com.yszln.qiuqiu.ui.main.view.MainActivity

abstract class BaseChatActivity : BaseLoadMoreActivity<ChatViewModel>() {
    lateinit var mSocketBind: SocketService.MyBinder
    val conn = MyConn()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindService(intent, conn, Context.BIND_AUTO_CREATE)
    }

    override fun onDestroy() {
        unbindService(conn)
        super.onDestroy()
    }

    inner class MyConn : ServiceConnection {
        override fun onServiceDisconnected(p0: ComponentName?) {

        }

        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            mSocketBind = (p1 as SocketService.MyBinder)
        }

    }
}