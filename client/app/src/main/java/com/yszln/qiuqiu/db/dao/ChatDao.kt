package com.yszln.qiuqiu.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.yszln.qiuqiu.db.table.TbChat
//import com.yszln.qiuqiu.db.table.TbChat
import com.yszln.qiuqiu.db.table.TbMessage


@Dao
abstract class ChatDao : BaseDao<TbChat> {

    @Query("select * from tb_chat")
    abstract fun findAll(): MutableList<TbChat>

    @Query("select * from tb_chat where friendId=:id")
    abstract fun findByFriend(id: Long?): MutableList<TbChat>

    @Query("delete  from tb_chat where friendId=:id")
    abstract fun deleteByFriend(id: Long?)
}
