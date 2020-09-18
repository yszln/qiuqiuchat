package com.yszln.qiuqiu.ui.main.adapter

import android.os.Bundle
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yszln.lib.adapter.CommonAdapter
import com.yszln.lib.adapter.loadRound
import com.yszln.lib.utils.date2TimeStamp
import com.yszln.lib.utils.start
import com.yszln.lib.utils.toJson
import com.yszln.qiuqiu.R
import com.yszln.qiuqiu.db.table.TbChat
import com.yszln.qiuqiu.db.table.TbFriend
import com.yszln.qiuqiu.ui.chat.view.FriendChatActivity
import com.yszln.qiuqiu.utils.Constant

class HomeChatAdapter : CommonAdapter<TbChat>(R.layout.item_rv_home_chat) {

    init {
        setOnItemClickListener { adapter, view, position ->
            data[position].apply {
                start(
                    FriendChatActivity::class.java,
                    Bundle().apply {
                        putString(
                            Constant.JSON,
                            TbFriend(friendId, friendName, friendAvatar, 0).toJson()
                        )
                    })
            }

        }
    }



    override fun convert(holder: BaseViewHolder, item: TbChat) {
        holder.setText(R.id.item_rv_home_chat_username, item.friendName)
            .setText(R.id.item_rv_home_chat_content, item.content)
            .setText(R.id.item_rv_home_chat_time, item.time.date2TimeStamp())
            .loadRound(R.id.item_rv_home_chat_avatar, item.friendAvatar,5)
    }


}