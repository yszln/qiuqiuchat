package com.yszln.qiuqiu.ui.chat.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.yszln.lib.bus.LiveDataBus
import com.yszln.lib.fragment.BaseFragment
import com.yszln.lib.utils.jsonFormat
import com.yszln.lib.utils.textStr
import com.yszln.qiuqiu.R
import com.yszln.qiuqiu.db.CacheDataBase
import com.yszln.qiuqiu.db.UserUtils
import com.yszln.qiuqiu.db.table.TbFriend
import com.yszln.qiuqiu.db.table.TbMessage
import com.yszln.qiuqiu.ui.chat.adapter.FriendChatAdapter
import com.yszln.qiuqiu.ui.chat.model.ChatEnum
import com.yszln.qiuqiu.ui.chat.viewmodel.ChatViewModel
import com.yszln.qiuqiu.ui.main.view.MainActivity
import com.yszln.qiuqiu.utils.Constant
import com.yszln.qiuqiu.weiget.VoiceView
import kotlinx.android.synthetic.main.activity_chat.*
import java.io.File

class FriendChatFragment : BaseFragment<ChatViewModel>() {

    private var mUser: TbFriend? = null

    private val mAdapter = FriendChatAdapter()


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
            liveFiles.observe(this@FriendChatFragment, Observer {

                mAdapter.addData((mActivity as MainActivity). sendMessage("", it[0], ChatEnum.MESSAGE_VOICE))
                scrollBottom()
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
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        CacheDataBase.instance.messageDao()
            .findFriendMessage(mUser?.id, UserUtils.getLoginUser().id).apply {
                mAdapter.setNewInstance(this)
                scrollBottom();
            }

    }


    override fun initIntentData() {
        arguments?.getString(Constant.JSON)?.apply {
            mUser = jsonFormat(TbFriend::class.java)
        }

    }







    override fun onDestroy() {
//        unbindService(mChatConn)
        super.onDestroy()
    }

    override fun registerClick(): MutableList<View> {
        return mutableListOf(chatSend)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            chatSend.id -> {
                val text = chatInput.textStr()
                if (text.isEmpty()) return
                chatInput.setText("")
                (mActivity as MainActivity).sendMessage(text, "", ChatEnum.MESSAGE_TEXT)
            }
        }
    }


}