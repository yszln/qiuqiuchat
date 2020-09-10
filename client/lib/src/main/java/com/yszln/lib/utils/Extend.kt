package com.yszln.lib.utils

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.yszln.lib.app.AppManageHelper
import com.yszln.lib.app.BaseApplication


/**
 * 扩展方法
 */


fun String.toast() {
    ToastUtils.showToast(this)
}

fun <T> String.jsonFormat(clazz: Class<T>): T {
    return JsonUtils.format(this, clazz)
}


fun Any.toJson(): String {
    return JsonUtils.toJson(this)
}

fun Any.getString(id: Int): String {
    return BaseApplication.instance.getString(id)
}

fun Any.start(clazz: Class<*>, bundle: Bundle? = null) {
    val context = AppManageHelper.currentActivity()
    val intent = Intent(context, clazz);
    bundle?.let { intent.putExtras(it) }
    context.startActivity(intent)
}


fun TextView.textStr(): String {
    return text.toString().trim()
}

