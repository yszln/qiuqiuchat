package com.yszln.lib.utils

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.yszln.lib.app.AppManageHelper
import com.yszln.lib.app.BaseApplication
import java.text.SimpleDateFormat
import java.util.*


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
    AppManageHelper.currentActivity().apply {
        val intent = Intent(this, clazz);
        bundle?.let { intent.putExtras(it) }
        startActivity(intent)
    }

}

/**
 * 日期格式字符串转换成时间戳
 * @param date 字符串日期
 * @param format 如：yyyy-MM-dd HH:mm:ss
 * @return
 */
fun Long.date2TimeStamp(): String {
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    return simpleDateFormat.format(Date(this * 1000))

}


fun TextView.textStr(): String {
    return text.toString().trim()
}


fun ImageView.load(url: Any) {
    ImageLoader.load(this, url)
}

fun ImageView.load(url: Any, roundedCorners: Int) {
    ImageLoader.load(this, url, roundedCorners)
}
