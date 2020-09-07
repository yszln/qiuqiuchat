package com.yszln.lib.bean

import com.google.gson.annotations.SerializedName
import com.yszln.lib.network.ApiException
import com.yszln.lib.utils.JsonUtils
import com.yszln.lib.utils.LogUtil
import com.yszln.lib.utils.toJson

data class BaseBean<T>(
    @SerializedName("message") val message: String,
    @SerializedName("code") val code: String,
    private val data: T
) {

    fun data(): T {
        return if (code == "200") {
            data
        } else {
            throw ApiException(code, message)
        }
        null
    }
}
