package com.yszln.qiuqiu.ui.main.adapter

import android.os.Bundle
import androidx.navigation.Navigation
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yszln.lib.adapter.CommonAdapter
import com.yszln.lib.adapter.loadRound
import com.yszln.lib.utils.toJson
import com.yszln.qiuqiu.R
import com.yszln.qiuqiu.db.table.TbFriend
import com.yszln.qiuqiu.utils.Constant

class LinkmanAdapter : CommonAdapter<TbFriend>(R.layout.item_rv_linkman) {

    init {
        setOnItemClickListener { adapter, view, position ->

            Navigation.findNavController(view)
                .navigate(R.id.action_mainFragment_to_FriendChatFragment, Bundle().apply {
                    putString(
                        Constant.JSON,
                        data[position].toJson()
                    )
                })
        }
    }

    override fun convert(holder: BaseViewHolder, item: TbFriend) {
        holder.setText(R.id.item_rv_linkman_username, item.username)
            .loadRound(R.id.item_rv_linkman_avatar, item.avatar, 5)
    }


}