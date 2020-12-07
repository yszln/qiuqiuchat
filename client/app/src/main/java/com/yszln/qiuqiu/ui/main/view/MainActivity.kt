package com.yszln.qiuqiu.ui.main.view

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.KeyEvent
import android.view.View
import androidx.navigation.findNavController
import com.yszln.lib.activity.RootActivity
import com.yszln.lib.activity.SuperActivity
import com.yszln.lib.fragment.BaseFragment
import com.yszln.lib.utils.MyStatusBar
import com.yszln.qiuqiu.R
import com.yszln.qiuqiu.db.CacheDataBase
import com.yszln.qiuqiu.db.UserUtils
import com.yszln.qiuqiu.db.table.TbChat
import com.yszln.qiuqiu.db.table.TbFriend
import com.yszln.qiuqiu.db.table.TbMessage
import com.yszln.qiuqiu.db.table.TbUser
import com.yszln.qiuqiu.service.WebSocketService
import com.yszln.qiuqiu.ui.chat.model.ChatEnum
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : RootActivity() {


    override fun layoutId() = R.layout.activity_main

    private var mUser: TbUser = UserUtils.getLoginUser()

    lateinit var mChantService: WebSocketService.MyBinder
    private val mChatConn = ChatConnection()

    override fun initView(savedInstanceState: Bundle?) {
        MyStatusBar(this)

        val intent = Intent(this, WebSocketService::class.java)
        startService(intent)
        bindService(Intent(this, WebSocketService::class.java), mChatConn, Context.BIND_AUTO_CREATE)
    }

    override fun onClick(v: View?) {
    }

    open fun sendMessage(content: String, url: String, chatEnum: ChatEnum): TbMessage {
        mChantService.send(mUser.id, content, url, chatEnum.value)
        val tbMessage = TbMessage(
            null,
            content,
            url,
            chatEnum.value,
            mUser.id,
            mUser.username,
            mUser.avatar,
            UserUtils.getLoginUser().id,
            UserUtils.getLoginUser().username,
            UserUtils.getLoginUser().avatar,
            System.currentTimeMillis() / 1000,
            ChatEnum.ONESELF.value
        )
        setChat(tbMessage)
        CacheDataBase.instance.messageDao().insert(tbMessage)
        return tbMessage

    }

    private fun setChat(tbMessage: TbMessage) {
        tbMessage.apply {
            val findByFriend = CacheDataBase.instance.chatDao().findByFriend(receiveId)
            if (findByFriend.size < 1) {
                val tbChat = TbChat(
                    null,
                    content,
                    receiveId,
                    receiveName,
                    receiveAvatar,
                    System.currentTimeMillis() / 1000
                )
                CacheDataBase.instance.chatDao().insert(tbChat)
            } else {
                findByFriend[0].content = content
                findByFriend[0].time = System.currentTimeMillis() / 1000
                CacheDataBase.instance.chatDao().deleteByFriend(receiveId)
                CacheDataBase.instance.chatDao().update(findByFriend[0])
            }
        }

    }


    inner class ChatConnection : ServiceConnection {
        override fun onServiceDisconnected(p0: ComponentName) {
        }

        override fun onServiceConnected(p0: ComponentName, p1: IBinder) {
            mChantService = (p1 as WebSocketService.MyBinder)
        }

    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (currentFragment.onBackPressed()) {
            findNavController(R.id.mainNavHost).popBackStack()
            return true
        }
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.addCategory(Intent.CATEGORY_HOME)
            startActivity(intent)
            return true
        }
        return super.onKeyDown(keyCode, event)
    }


}


