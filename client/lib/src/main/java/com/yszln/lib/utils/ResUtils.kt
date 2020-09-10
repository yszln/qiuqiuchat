package com.yszln.lib.utils

import androidx.core.content.ContextCompat
import com.yszln.lib.app.BaseApplication

object ResUtils {
    fun getColor(id: Int) = ContextCompat.getColor(BaseApplication.instance, id)
    fun getDrawable(id: Int) = ContextCompat.getDrawable(BaseApplication.instance, id)
}