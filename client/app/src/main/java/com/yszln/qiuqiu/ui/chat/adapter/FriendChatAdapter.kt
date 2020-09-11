package com.yszln.qiuqiu.ui.chat.adapter

import android.os.Bundle
import com.yszln.lib.adapter.CommonMultiItemLoadAdapter
import com.yszln.lib.adapter.CommonViewHolder
import com.yszln.lib.utils.start
import com.yszln.lib.utils.toJson
import com.yszln.qiuqiu.R
import com.yszln.qiuqiu.db.table.TbMessage
import com.yszln.qiuqiu.ui.chat.model.ChatEnum
import com.yszln.qiuqiu.ui.chat.view.FriendChatActivity
import com.yszln.qiuqiu.utils.Constant

/**
 * 好友聊天
 */
class FriendChatAdapter : CommonMultiItemLoadAdapter<TbMessage>() {

    init {
        addItemType(ChatEnum.ONESELF.value, R.layout.item_rv_friend_chat_message_oneself)
        addItemType(ChatEnum.OTHER.value, R.layout.item_rv_friend_chat_message_other)

    }


    override fun convert(holder: CommonViewHolder, item: TbMessage) {
        holder.setText(R.id.item_rv_message_text,item.content)
        when(item.itemType){
            ChatEnum.ONESELF.value->{
                setOneselfConvert(holder,item)
            }
            ChatEnum.OTHER.value->{
                setOtherConvert(holder,item)
            }
        }


    }

    private fun setOtherConvert(holder: CommonViewHolder, item: TbMessage) {

    }

    private fun setOneselfConvert(holder: CommonViewHolder, item: TbMessage) {

    }


}