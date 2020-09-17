package com.yszln.qiuqiu.ui.chat.adapter

import com.chad.library.adapter.base.viewholder.BaseViewHolder

import com.yszln.lib.adapter.CommonMultiItemLoadAdapter
import com.yszln.qiuqiu.R
import com.yszln.qiuqiu.db.table.TbMessage
import com.yszln.qiuqiu.ui.chat.model.ChatEnum

/**
 * 群聊
 */
class GroupChatAdapter : CommonMultiItemLoadAdapter<TbMessage>() {

    init {
        addItemType(ChatEnum.ONESELF.value, R.layout.item_rv_group_chat_message_oneself)
        addItemType(ChatEnum.OTHER.value, R.layout.item_rv_group_chat_message_other)

    }


    override fun convert(holder: BaseViewHolder, item: TbMessage) {
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

    private fun setOtherConvert(holder: BaseViewHolder, item: TbMessage) {
        item.apply {
            holder.setText(R.id.item_rv_message_username,sourceName)

        }
    }

    private fun setOneselfConvert(holder: BaseViewHolder, item: TbMessage) {
        item.apply {
            holder.setText(R.id.item_rv_message_username,receiveName)

        }
    }


}