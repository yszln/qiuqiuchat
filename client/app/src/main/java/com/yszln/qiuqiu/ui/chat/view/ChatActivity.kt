package com.yszln.qiuqiu.ui.chat.view

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.yszln.lib.activity.BaseVMActivity
import com.yszln.lib.utils.jsonFormat
import com.yszln.lib.utils.textStr
import com.yszln.lib.utils.toJson
import com.yszln.qiuqiu.R
import com.yszln.qiuqiu.db.table.TbUser
import com.yszln.qiuqiu.service.SocketService
import com.yszln.qiuqiu.ui.chat.model.SendMessageBean
import com.yszln.qiuqiu.ui.chat.viewmodel.ChatViewModel
import com.yszln.qiuqiu.utils.Constant
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : BaseVMActivity<ChatViewModel>() {

    lateinit var mChantService: SocketService.MyBinder
    val mChatConn = ChatConnection()

    var mUser: TbUser? = null


    override fun refreshData() {
    }

    override fun observe() {
    }

    override fun layoutId() = R.layout.activity_chat

    override fun initView() {
        initData()
        bindService(Intent(this, SocketService::class.java), mChatConn, Context.BIND_AUTO_CREATE)
    }

    private fun initData() {
        intent?.extras?.getString(Constant.JSON)?.apply {
            mUser = jsonFormat(TbUser::class.java)
        }
        mTitleBar.setTitle(mUser?.username)
    }

    override fun onClick() {
        chatSend.setOnClickListener {
            mChantService.send(SendMessageBean(mUser?.id,chatInput.textStr(),0).toJson())
        }
    }

    override fun onDestroy() {
        unbindService(mChatConn)
        super.onDestroy()
    }

    inner class ChatConnection : ServiceConnection {
        override fun onServiceDisconnected(p0: ComponentName) {
        }

        override fun onServiceConnected(p0: ComponentName, p1: IBinder) {
            mChantService = (p1 as SocketService.MyBinder)
        }

    }
}