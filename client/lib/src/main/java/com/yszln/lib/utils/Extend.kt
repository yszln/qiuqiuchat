package com.yszln.lib.utils

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.yszln.lib.app.AppManageHelper
import com.yszln.lib.app.BaseApplication
import java.io.Serializable
import java.lang.RuntimeException
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
inline fun <reified T> Any.start(vararg pairs: Pair<String, Any>) {
    AppManageHelper.currentActivity().apply {
        val intent = Intent(this, T::class.java)
        fillIntentArguments(intent,pairs)
        startActivity(intent)

    }
}

inline  fun fillIntentArguments(intent: Intent, params: Array<out Pair<String, Any?>>) {
    params.forEach {
        when (val value = it.second) {
            null -> intent.putExtra(it.first, null as Serializable?)
            is Int -> intent.putExtra(it.first, value)
            is Long -> intent.putExtra(it.first, value)
            is CharSequence -> intent.putExtra(it.first, value)
            is String -> intent.putExtra(it.first, value)
            is Float -> intent.putExtra(it.first, value)
            is Double -> intent.putExtra(it.first, value)
            is Char -> intent.putExtra(it.first, value)
            is Short -> intent.putExtra(it.first, value)
            is Boolean -> intent.putExtra(it.first, value)
            is Serializable -> intent.putExtra(it.first, value)
            is Bundle -> intent.putExtra(it.first, value)
            is Parcelable -> intent.putExtra(it.first, value)
            is Array<*> -> when {
                value.isArrayOf<CharSequence>() -> intent.putExtra(it.first, value)
                value.isArrayOf<String>() -> intent.putExtra(it.first, value)
                value.isArrayOf<Parcelable>() -> intent.putExtra(it.first, value)
                else -> throw RuntimeException("Intent extra ${it.first} has wrong type ${value.javaClass.name}")
            }
            is IntArray -> intent.putExtra(it.first, value)
            is LongArray -> intent.putExtra(it.first, value)
            is FloatArray -> intent.putExtra(it.first, value)
            is DoubleArray -> intent.putExtra(it.first, value)
            is CharArray -> intent.putExtra(it.first, value)
            is ShortArray -> intent.putExtra(it.first, value)
            is BooleanArray -> intent.putExtra(it.first, value)
            else -> throw RuntimeException("Intent extra ${it.first} has wrong type ${value.javaClass.name}")
        }
        return@forEach
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

fun Int.px() = ScreenUtil.dpToPx(this)
fun Float.px() = ScreenUtil.dpToPx(this)
