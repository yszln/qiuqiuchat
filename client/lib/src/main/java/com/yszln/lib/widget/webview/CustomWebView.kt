package com.yszln.lib.widget.webview

import android.content.Context
import android.util.AttributeSet
import android.webkit.WebView

/**
 * @author: yszln
 * @date: 2020/8/10 9:57
 * @description:自定义webview
 * @history:
 */
class CustomWebView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : WebView(WebViewHelper.getFixedContext(context), attrs, defStyleAttr) {

    init {
        settings.javaScriptEnabled = true

    }


}


