package com.yszln.qiuqiu.ui.chat.view

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.yszln.lib.activity.BaseVMActivity
import com.yszln.lib.bus.LiveDataBus
import com.yszln.lib.utils.jsonFormat
import com.yszln.lib.utils.textStr
import com.yszln.lib.utils.toJson
import com.yszln.qiuqiu.R
import com.yszln.qiuqiu.db.CacheDataBase
import com.yszln.qiuqiu.db.UserUtils
import com.yszln.qiuqiu.db.table.TbMessage
import com.yszln.qiuqiu.db.table.TbUser
import com.yszln.qiuqiu.service.WebSocketService
import com.yszln.qiuqiu.ui.chat.adapter.FriendChatAdapter
import com.yszln.qiuqiu.ui.chat.model.ChatEnum
import com.yszln.qiuqiu.ui.chat.model.SendMessageBean
import com.yszln.qiuqiu.ui.chat.viewmodel.ChatViewModel
import com.yszln.qiuqiu.utils.Constant
import kotlinx.android.synthetic.main.activity_chat.*

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
            }
        })
    }

    override fun layoutId() = R.layout.activity_chat

    override fun initView() {
        mRecyclerView.adapter = mAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        initData()
        CacheDataBase.instance.messageDao()
            .findFriendMessage(mUser?.id ?: 0L, UserUtils.getLoginUser().id).apply {
                mAdapter.setNewInstance(this)
                if(size>0){
                    mRecyclerView.smoothScrollToPosition(mAdapter.data.size-1)
                }

            }
        bindService(Intent(this, WebSocketService::class.java), mChatConn, Context.BIND_AUTO_CREATE)
    }

    private fun initData() {
        intent?.extras?.getString(Constant.JSON)?.apply {
            mUser = jsonFormat(TbUser::class.java)
        }
        mTitleBar.setTitle(mUser?.username)
    }

    override fun onClick() {
        chatSend.setOnClickListener {
            val text = chatInput.textStr()
            chatInput.setText("")
            val sendMessageBean = SendMessageBean(
                mUser?.id,
                text,
                ChatEnum.MESSAGE_TEXT.value
            )
            mChantService.send(sendMessageBean.toJson())
            val tbMessage = TbMessage(
                0, text,
                "",
                ChatEnum.MESSAGE_TEXT.value,
                mUser?.id!!,
                "",
                UserUtils.getLoginUser().id,
                UserUtils.getLoginUser().username,
                System.currentTimeMillis(),
                ChatEnum.ONESELF.value
            )
            mAdapter.addData(tbMessage)
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