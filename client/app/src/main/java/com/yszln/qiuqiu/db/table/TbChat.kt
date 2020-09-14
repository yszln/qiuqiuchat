package com.yszln.qiuqiu.db.table

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "tb_chat")
data class TbChat(
    @PrimaryKey
    var id: Long,
    var userId: Long,//聊天的用户
    var messageId: Long,//最后一个消息id
    @Ignore
    var user: TbUser,
    @Ignore
    var message: TbMessage
)