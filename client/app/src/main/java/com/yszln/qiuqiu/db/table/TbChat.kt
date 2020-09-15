package com.yszln.qiuqiu.db.table

import androidx.room.*

/**
 * 聊天列表
 */
@Entity(tableName = "tb_chat")
data class TbChat(
    @PrimaryKey
    var id: Long?,
    var content: String?,//消息预览
    var friendId: Long,//好友id
    var friendName: String,//好友名称
    var friendAvatar: String,//好友头像
    var time: Long//最后聊天时间
)
