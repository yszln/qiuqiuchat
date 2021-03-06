package com.yszln.lib.utils

import com.google.gson.Gson


object JsonUtils {
    fun toJson(any: Any): String {
        return Gson().toJson(any)
    }

    fun <T> format(json: String, clazz: Class<T>): T {
        return Gson().fromJson(json, clazz)
    }


}