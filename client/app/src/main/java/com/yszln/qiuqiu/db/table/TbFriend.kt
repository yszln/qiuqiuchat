package com.yszln.qiuqiu.db.table

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_friend")
data class TbFriend(
    @PrimaryKey var id: Long,
    var username: String,
    var avatar: String,
    var status: Int
) {
}