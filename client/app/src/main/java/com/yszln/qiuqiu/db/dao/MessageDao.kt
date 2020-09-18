package com.yszln.qiuqiu.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.yszln.qiuqiu.db.table.TbMessage

@Dao
interface MessageDao : BaseDao<TbMessage> {
    @Query("select * from tb_message")
    fun findAll(): MutableList<TbMessage>

    @Query("select * from tb_message where (sourceId=:sourceId and receiveId=:receiveId) or (sourceId=:receiveId and receiveId=:sourceId)")
    fun findFriendMessage(sourceId: Long?, receiveId: Long): MutableList<TbMessage>
}