package com.yszln.lib.utils

import androidx.core.content.ContextCompat
import com.yszln.lib.BaseApplication

object ResUtils {
    fun getColor(id: Int) = ContextCompat.getColor(BaseApplication.mContext, id)
    fun getDrawable(id: Int) = ContextCompat.getDrawable(BaseApplication.mContext, id)
}