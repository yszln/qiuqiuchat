package com.yszln.qiuqiu.utils

import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.widget.TextView
import androidx.lifecycle.MutableLiveData

class TextWatchUtils : TextWatcher {

    private val inputs = mutableListOf<TextView>()
    val isEnable = MutableLiveData<Boolean>()

    fun add(input: TextView) {
        inputs.add(input)
        input.addTextChangedListener(this)
    }

    override fun beforeTextChanged(char: CharSequence, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(char: CharSequence, p1: Int, p2: Int, p3: Int) {

    }

    override fun afterTextChanged(e: Editable) {
        var enable = true
        for (input in inputs) {
            if (TextUtils.isEmpty(input.text.toString().trim())) {
                enable = false
                break
            }
        }
        isEnable.value = enable
    }


}