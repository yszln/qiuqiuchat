package com.yszln.qiuqiu.ui.chat.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Handler
import android.os.IBinder
import android.view.MotionEvent
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.yszln.lib.activity.BaseVMActivity
import com.yszln.lib.bus.LiveDataBus
import com.yszln.lib.utils.LogUtil
import com.yszln.lib.utils.jsonFormat
import com.yszln.lib.utils.textStr
import com.yszln.lib.utils.toJson
import com.yszln.qiuqiu.R
import com.yszln.qiuqiu.db.CacheDataBase
import com.yszln.qiuqiu.db.UserUtils
import com.yszln.qiuqiu.db.table.TbChat
import com.yszln.qiuqiu.db.table.TbMessage
import com.yszln.qiuqiu.db.table.TbUser
import com.yszln.qiuqiu.service.WebSocketService
import com.yszln.qiuqiu.ui.chat.adapter.FriendChatAdapter
import com.yszln.qiuqiu.ui.chat.model.ChatEnum
import com.yszln.qiuqiu.ui.chat.model.SendMessageBean
import com.yszln.qiuqiu.ui.chat.viewmodel.ChatViewModel
import com.yszln.qiuqiu.utils.Constant
import com.yszln.qiuqiu.utils.MediaUtils
import com.yszln.qiuqiu.weiget.VoiceView
import kotlinx.android.synthetic.main.activity_chat.*
import java.io.File
import java.lang.Exception

class FriendChatActivity : BaseVMActivity<ChatViewModel>() {

    lateinit var mChantService: WebSocketService.MyBinder
    val mChatConn = ChatConnection()

    var mUser: TbUser? = null

    val mAdapter = FriendChatAdapter()


    override fun refreshData() {
    }

    override fun observe() {
        LiveDataBus.getChannel(Constant.SEND_MESSAGE).observe(this, Observer {
            val message = it as TbMessage
            if (message.sourceId == mUser?.id) {
                //是当前聊天的数据
                mAdapter.addData(message)
                scrollBottom()
            }
        })
        mViewModel.apply {
            liveFiles.observe(this@FriendChatActivity, Observer {
                sendMessage(it[0], ChatEnum.MESSAGE_VOICE)
            })
        }
    }

    private fun scrollBottom() {
        try {
            mRecyclerView.scrollToPosition(mAdapter.itemCount - 1)
        } catch (e: Exception) {
        }

    }

    override fun layoutId() = R.layout.activity_chat

    override fun initView() {
        mRecyclerView.adapter = mAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        initData()
        LogUtil.e(CacheDataBase.instance.messageDao().findAll().toJson())
        CacheDataBase.instance.messageDao()
            .findFriendMessage(mUser?.id, UserUtils.getLoginUser().id).apply {
                mAdapter.setNewInstance(this)
                scrollBottom();
            }
        bindService(Intent(this, WebSocketService::class.java), mChatConn, Context.BIND_AUTO_CREATE)
    }

    private fun initData() {
        intent?.extras?.getString(Constant.JSON)?.apply {
            mUser = jsonFormat(TbUser::class.java)
        }
        mTitleBar.setTitle(mUser?.username)

    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onClick() {
        chatSend.setOnClickListener {
            val text = chatInput.textStr()
            if (text.isEmpty()) return@setOnClickListener
            chatInput.setText("")
            sendMessage(text, ChatEnum.MESSAGE_TEXT)
        }

        /*  chatVoice.onMediaListener = object : VoiceView.OnMediaListener {
              override fun onEnd(file: File?) {
                  LogUtil.e("录音路径：${file?.absolutePath}")
              }
          }*/

        chatVoice.onMediaListener = object : VoiceView.OnMediaListener {
            override fun onEnd(filePath: String) {
                mViewModel.upload(mutableListOf(File(filePath)))
            }

        }
    }

    private fun sendMessage(text: String, chatEnum: ChatEnum) {
        mChantService.send(mUser?.id, text, chatEnum.value)
        val tbMessage = TbMessage(
            null,
            text,
            "",
            chatEnum.value,
            mUser?.id!!,
            mUser?.username ?: "",
            mUser?.avatar ?: "",
            UserUtils.getLoginUser().id,
            UserUtils.getLoginUser().username,
            UserUtils.getLoginUser().avatar,
            System.currentTimeMillis() / 1000,
            ChatEnum.ONESELF.value
        )
        setChat(tbMessage)
        CacheDataBase.instance.messageDao().insert(tbMessage)
        mAdapter.addData(tbMessage)
        scrollBottom()
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

    override fun onDestroy() {
        unbindService(mChatConn)
        super.onDestroy()
    }

    inner class ChatConnection : ServiceConnection {
        override fun onServiceDisconnected(p0: ComponentName) {
        }

        override fun onServiceConnected(p0: ComponentName, p1: IBinder) {
            mChantService = (p1 as WebSocketService.MyBinder)
        }

    }
}