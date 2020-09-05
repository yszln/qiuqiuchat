package com.yszln.lib.widget.webview

import android.content.Context
import android.content.res.Configuration
import android.os.Build

object WebViewHelper {
    /**
     * 修复迁移androidx在安卓5上闪退的问题
     *  Android Lollipop 5.0 & 5.1
     */
    fun getFixedContext(context: Context): Context {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            context.createConfigurationContext(Configuration())
        } else context
    }
}