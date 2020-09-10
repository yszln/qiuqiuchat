package com.yszln.qiuqiu.ui.main.adapter

import android.os.Bundle
import com.yszln.lib.adapter.CommonAdapter
import com.yszln.lib.adapter.CommonViewHolder
import com.yszln.lib.utils.start
import com.yszln.lib.utils.toJson
import com.yszln.qiuqiu.R
import com.yszln.qiuqiu.db.table.TbUser
import com.yszln.qiuqiu.ui.chat.view.ChatActivity
import com.yszln.qiuqiu.utils.Constant

class LinkmanAdapter : CommonAdapter<TbUser>(R.layout.item_rv_linkman) {

    init {
        setOnItemClickListener { adapter, view, position ->
            start(
                ChatActivity::class.java,
                Bundle().apply { putString(Constant.JSON, data[position].toJson()) })
        }
    }

    override fun convert(holder: CommonViewHolder, item: TbUser) {
        holder.setText(R.id.item_rv_linkman_username, item.username)
    }


}