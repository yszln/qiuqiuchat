package com.yszln.lib.widget.input

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

open class SearchEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatEditText(context, attrs, defStyleAttr) {

    var mHandler = Handler(Looper.getMainLooper(), Handler.Callback {
        onSearchListener?.onSearch(it.obj.toString())
        true
    })

    var onSearchListener: OnSearchListener? = null

    init {
        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                mHandler.removeCallbacksAndMessages(null)
                val message = Message.obtain()
                message.obj = editable
                mHandler.sendMessageDelayed(message, 500)
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })
    }

    interface OnSearchListener {
        fun onSearch(text: String)
    }

    override fun onDetachedFromWindow() {
        mHandler.removeCallbacksAndMessages(null)
        super.onDetachedFromWindow()
    }

}