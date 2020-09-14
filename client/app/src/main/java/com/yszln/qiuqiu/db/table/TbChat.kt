package com.yszln.qiuqiu.db.table

import androidx.room.*

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

/*
data class FriendWithGiftList (

    @Embedded
    var friendInfo: FriendInfo,

    @Relation(parentColumn = "friendId", // 这个参数来自于FriendInfo的唯一Key
        entityColumn = "giftId", // 这个参数来自于GiftInfo的唯一Key
        entity = GiftInfo::class,
        associateBy = Junction(FriendGiftRelation::class))
    var giftList: List<GiftInfo>
) : Serializable
*/
