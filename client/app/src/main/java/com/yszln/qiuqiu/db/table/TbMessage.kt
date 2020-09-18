package com.yszln.qiuqiu.db.table

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.yszln.qiuqiu.ui.chat.model.ChatEnum

@Entity(tableName = "tb_message")
data class TbMessage(
    @PrimaryKey
    var id: Long?,
    var content: String?,//消息内容
    var url: String?,//消息url
    var type: Int,//消息类型
    var receiveId: Long,//接收人
    var receiveName: String,//接收人名称
    var receiveAvatar: String,
    var sourceId: Long,//发送人
    var sourceName: String,//发送人姓名
    var sourceAvatar: String,
    var time: Long,//创建时间
    override var itemType: Int
) : MultiItemEntity {
    fun showContent() = when (type) {
        ChatEnum.MESSAGE_TEXT.value -> {
            content
        }
        ChatEnum.MESSAGE_VOICE.value -> {
            "语音消息"
        }ChatEnum.MESSAGE_VIDEO.value -> {
            "视频消息"
        }
        ChatEnum.MESSAGE_IMAGE.value -> {
            "图片消息"
        }
        ChatEnum.MESSAGE_VIDEO.value -> {
            "视频消息"
        }
        else -> {
            "请升级到最新版查看"
        }
    }
}