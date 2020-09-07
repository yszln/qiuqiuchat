package com.yszln.qiuqiu.db.table

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_message")
data class TbMessage(
    @PrimaryKey
    var id :Long,
    var content:String?,
    var url:String?,
    var type:Int,
    var sourceId:Int
)