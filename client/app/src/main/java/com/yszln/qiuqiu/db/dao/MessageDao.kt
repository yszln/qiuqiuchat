package com.yszln.qiuqiu.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.yszln.qiuqiu.db.table.TbMessage
import com.yszln.qiuqiu.db.table.TbUser

@Dao
interface MessageDao : BaseDao<TbMessage> {
    @Query("select * from tb_message")
    fun findAll(): MutableList<TbMessage>

    @Query("select * from tb_message where sourceId=:sourceId or sourceId=:receiveId or receiveId=:sourceId or receiveId=:receiveId")
    fun findFriendMessage(sourceId:Long?,receiveId:Long): MutableList<TbMessage>
}