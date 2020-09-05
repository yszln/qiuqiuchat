package com.yszln.lib.utils

import android.content.Context
import android.content.SharedPreferences
import com.yszln.lib.BaseApplication

object SPUtils {
    private const val spName = "wanAndroidCache"

    val mSp: SharedPreferences =
        BaseApplication.mContext.getSharedPreferences(spName, Context.MODE_PRIVATE)


    public fun get(key: String): String? {
        return mSp.getString(key, "")
    }

    fun put(key: String, value: String) {
        mSp.edit().putString(key, value).apply();
    }


}