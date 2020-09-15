package com.yszln.qiuqiu.db.table

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_user")
data class TbUser(
    @PrimaryKey var id: Long,
    var username: String,
    var avatar: String,
    var status: Int
)