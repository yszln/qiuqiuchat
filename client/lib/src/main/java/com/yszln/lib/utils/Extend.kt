package com.yszln.lib.utils

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yszln.lib.BaseApplication


/**
 * 扩展方法
 */


fun String.toast() {
    ToastUtils.showToast(this)
}


fun Any.toJson(): String {
    return JsonUtils.toJson(this)
}
fun Any.getString(id:Int): String {
    return BaseApplication.mContext.getString(id)
}

fun Context.start(clazz: Class<*>, bundle: Bundle? = null) {
    val intent = Intent(this, clazz);
    bundle?.let { intent.putExtras(it) }
    startActivity(intent)
}



fun Fragment.start(clazz: Class<*>, bundle: Bundle?) {
    context?.start(clazz, bundle)
}

fun TextView.textStr():String{
    return text.toString().trim()
}

