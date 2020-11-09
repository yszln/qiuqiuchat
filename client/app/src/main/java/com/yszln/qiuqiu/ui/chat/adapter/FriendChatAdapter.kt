package com.yszln.qiuqiu.ui.chat.adapter

import android.view.View
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yszln.lib.adapter.CommonMultiItemAdapter
import com.yszln.lib.adapter.loadRound
import com.yszln.qiuqiu.R
import com.yszln.qiuqiu.db.table.TbMessage
import com.yszln.qiuqiu.ui.chat.model.ChatEnum
import com.yszln.qiuqiu.utils.MediaUtils

/**
 * 好友聊天
 */
class FriendChatAdapter : CommonMultiItemAdapter<TbMessage>() {

    init {
        addItemType(ChatEnum.ONESELF.value, R.layout.item_rv_friend_chat_message_oneself)
        addItemType(ChatEnum.OTHER.value, R.layout.item_rv_friend_chat_message_other)

    }


    override fun convert(holder: BaseViewHolder, item: TbMessage) {
        holder.setText(R.id.item_rv_message_text, item.showContent())

        when (item.itemType) {
            ChatEnum.ONESELF.value -> {
                setOneselfConvert(holder, item)
            }
            ChatEnum.OTHER.value -> {
                setOtherConvert(holder, item)
            }
        }

        holder.getView<View>(R.id.item_rv_message_text).setOnClickListener {

            MediaUtils.startPlaying(item.url)
        }


    }

    private fun setOtherConvert(holder: BaseViewHolder, item: TbMessage) {
        holder.loadRound(R.id.item_rv_message_avatar, item.sourceAvatar,5)
    }

    private fun setOneselfConvert(holder: BaseViewHolder, item: TbMessage) {
        holder.loadRound(R.id.item_rv_message_avatar, item.sourceAvatar,5)
    }




}