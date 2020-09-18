package com.yszln.qiuqiu.weiget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatImageView
import com.yszln.lib.utils.LogUtil
import com.yszln.qiuqiu.utils.MediaUtils

class VoiceView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    init {
        isLongClickable=true
    }

    var start = ""

    override fun onTouchEvent(event: MotionEvent): Boolean {
        LogUtil.e("event:${event.action}")

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                start = MediaUtils.startRecord()
                LogUtil.e("event down :${event.action},url:${start}")
            }
            MotionEvent.ACTION_UP -> {
                MediaUtils.stopRecord()
                LogUtil.e("event up :${event.action},url:${start}")
                onMediaListener?.onEnd(start)
            }
        }
        return super.onTouchEvent(event)
    }

    interface OnMediaListener {
        fun onEnd(file: String)
    }

    var onMediaListener: OnMediaListener? = null
}