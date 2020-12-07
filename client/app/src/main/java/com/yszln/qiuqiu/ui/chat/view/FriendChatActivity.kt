package com.yszln.qiuqiu.ui.chat.view

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import cn.dreamtobe.kpswitch.util.KeyboardUtil
import com.yszln.lib.activity.BaseActivity
import com.yszln.lib.bus.LiveDataBus
import com.yszln.lib.utils.jsonFormat
import com.yszln.lib.utils.textStr
import com.yszln.qiuqiu.R
import com.yszln.qiuqiu.db.CacheDataBase
import com.yszln.qiuqiu.db.UserUtils
import com.yszln.qiuqiu.db.table.TbChat
import com.yszln.qiuqiu.db.table.TbFriend
import com.yszln.qiuqiu.db.table.TbMessage
import com.yszln.qiuqiu.service.WebSocketService
import com.yszln.qiuqiu.ui.chat.adapter.FriendChatAdapter
import com.yszln.qiuqiu.ui.chat.model.ChatEnum
import com.yszln.qiuqiu.ui.chat.viewmodel.ChatViewModel
import com.yszln.qiuqiu.utils.Constant
import com.yszln.qiuqiu.weiget.VoiceView
import kotlinx.android.synthetic.main.activity_chat.*
import java.io.File

class FriendChatActivity : BaseActivity<ChatViewModel>() {

    lateinit var mChantService: WebSocketService.MyBinder
    val mChatConn = ChatConnection()

    var mUser: TbFriend? = null

    val mAdapter = FriendChatAdapter()



    override fun observer() {
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
                sendMessage("", it[0], ChatEnum.MESSAGE_VOICE)
            })
        }


    }

    override fun refreshData() {

    }

    private fun scrollBottom() {
        try {
            mRecyclerView.scrollToPosition(mAdapter.itemCount - 1)
        } catch (e: Exception) {
        }

    }

    override fun layoutId() = R.layout.activity_chat



    override fun initView(savedInstanceState: Bundle?) {
        mTitleBar.setTitle(mUser?.username)
        chatVoice.onMediaListener = object : VoiceView.OnMediaListener {
            override fun onEnd(filePath: String) {
                mViewModel.upload(mutableListOf(File(filePath)))
            }

        }
        mRecyclerView.adapter = mAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        CacheDataBase.instance.messageDao()
            .findFriendMessage(mUser?.id, UserUtils.getLoginUser().id).apply {
                mAdapter.setNewInstance(this)
                scrollBottom();
            }
        bindService(Intent(this, WebSocketService::class.java), mChatConn, Context.BIND_AUTO_CREATE)

        KeyboardUtil.attach(
            this,
            panelFrameLayout,
            object : KeyboardUtil.OnKeyboardShowingListener {
                override fun onKeyboardShowing(isShowing: Boolean) {
                    panelFrameLayout.visibility=if(isShowing) View.VISIBLE else View.GONE
                }
            });

        /* KPSwitchConflictUtil.attach(
             panelFrameLayout, chatInput,object :KPSwitchConflictUtil.SwitchClickListener{
                 override fun onClickSwitch(v: View?, switchToPanel: Boolean) {
                     switchToPanel.toString().toast()
                 }
             }
         )*/
    }

    override fun onPause() {
        super.onPause()
//        panelFrameLayout.recordKeyboardStatus(window)
    }

    override fun initIntentData() {
        intent?.extras?.getString(Constant.JSON)?.apply {
            mUser = jsonFormat(TbFriend::class.java)
        }

    }


    private fun sendMessage(content: String, url: String, chatEnum: ChatEnum) {
        mChantService.send(mUser?.id, content, url, chatEnum.value)
        val tbMessage = TbMessage(
            null,
            content,
            url,
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

    override fun registerClick(): MutableList<View> {
        return mutableListOf(chatSend)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            chatSend.id->{
                val text = chatInput.textStr()
                if (text.isEmpty()) return
                chatInput.setText("")
                sendMessage(text, "", ChatEnum.MESSAGE_TEXT)
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
}