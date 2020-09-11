package com.yszln.lib.bean

import com.google.gson.annotations.SerializedName
import com.yszln.lib.network.ApiException
import com.yszln.lib.network.ApiExceptionHandler
import com.yszln.lib.utils.JsonUtils
import com.yszln.lib.utils.LogUtil
import com.yszln.lib.utils.start
import com.yszln.lib.utils.toJson

data class BaseBean<T>(
    @SerializedName("message") val message: String,
    @SerializedName("code") val code: Int,
    private val data: T
) {

    fun data(): T {
        return when (code) {
            200 -> {
                data
            }
            else -> {
                throw ApiException(code, message)
            }
        }

    }

    fun isSuccess()= code==200
}
