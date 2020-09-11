package com.yszln.qiuqiu.ui.chat.model

/**
 *  消息类型,0：系统推送，1：普通消息，2：语音消息，3：图片消息，4：视频消息，5：其他文件消息
 */
enum class ChatEnum(val value:Int) {
    ONESELF(1001),
    OTHER(1002),
    MESSAGE_PUSH(0),
    MESSAGE_TEXT(1),
    MESSAGE_VOICE(2),
    MESSAGE_IMAGE(3),
    MESSAGE_VIDEO(4),
}