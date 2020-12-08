package com.yszln.qiuqiu.ui.main.view

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.KeyEvent
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.yszln.lib.activity.RootActivity
import com.yszln.lib.utils.MyStatusBar
import com.yszln.qiuqiu.R
import com.yszln.qiuqiu.db.CacheDataBase
import com.yszln.qiuqiu.db.UserUtils
import com.yszln.qiuqiu.db.table.TbChat
import com.yszln.qiuqiu.db.table.TbMessage
import com.yszln.qiuqiu.db.table.TbUser
import com.yszln.qiuqiu.service.WebSocketService
import com.yszln.qiuqiu.ui.chat.model.ChatEnum
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : RootActivity() {

    companion object{
        lateinit var mNavController: NavController
    }

    override fun layoutId() = R.layout.activity_main


    private var mUser: TbUser = UserUtils.getLoginUser()

    lateinit var mChantService: WebSocketService.MyBinder
    private val mChatConn = ChatConnection()

    override fun initView(savedInstanceState: Bundle?) {
        window.setBackgroundDrawableResource(R.color.white)
        MyStatusBar(this)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            requestPermissions(
                arrayOf(
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ), 0
            )
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)


        //启动长连接服务
        val intent = Intent(this, WebSocketService::class.java)
        startService(intent)
        bindService(Intent(this, WebSocketService::class.java), mChatConn, Context.BIND_AUTO_CREATE)

        mNavController = findNavController(R.id.mainNavHost)

        mNavController.setGraph(R.navigation.nav_graph)
        if (UserUtils.checkLogin()) {
            mNavController.navigate(R.id.mainFragment)
        }else{
            mNavController.navigate(R.id.loginFragment)
        }
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

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (currentFragment.onBackPressed()) {
                findNavController(R.id.mainNavHost).popBackStack()
                return true
            }
            val intent = Intent(Intent.ACTION_MAIN)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.addCategory(Intent.CATEGORY_HOME)
            startActivity(intent)
            return true
        }
        return super.onKeyDown(keyCode, event)
    }


}


