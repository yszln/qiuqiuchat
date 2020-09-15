package com.yszln.qiuqiu.ui.main.adapter

import android.os.Bundle
import com.yszln.lib.adapter.CommonAdapter
import com.yszln.lib.adapter.CommonViewHolder
import com.yszln.lib.utils.start
import com.yszln.lib.utils.toJson
import com.yszln.qiuqiu.R
import com.yszln.qiuqiu.db.table.TbChat
import com.yszln.qiuqiu.db.table.TbUser
import com.yszln.qiuqiu.ui.chat.view.FriendChatActivity
import com.yszln.qiuqiu.utils.Constant

class HomeChatAdapter : CommonAdapter<TbChat>(R.layout.item_rv_home_chat) {

    init {
        setOnItemClickListener { adapter, view, position ->
            start(
                FriendChatActivity::class.java,
                Bundle().apply { putString(Constant.JSON, data[position].toJson()) })
        }
    }

    override fun convert(holder: CommonViewHolder, item: TbChat) {
        holder.setText(R.id.item_rv_home_chat_username, item.friendName)
            .setText(R.id.item_rv_home_chat_content,item.content)
            .setText(R.id.item_rv_home_chat_time,item.time.toString())

    }


}