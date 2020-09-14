package com.yszln.qiuqiu.db.dao

import androidx.room.Query
import com.yszln.qiuqiu.db.table.TbChat
import com.yszln.qiuqiu.db.table.TbMessage

abstract class ChatDao : BaseDao<TbChat> {

    @Query("select * from tb_chat")
    abstract fun findAll(): MutableList<TbMessage>

}