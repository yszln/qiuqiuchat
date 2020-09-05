package com.yszln.lib.network

import com.yszln.lib.BaseApplication
import com.yszln.lib.utils.ToastUtils
import java.lang.RuntimeException

/**
* @author: yszln
* @date: 2020/8/7 21:13
* @description: 自定义Api错误
* @history:
*/
class ApiException(val code: String, message: String) : Exception(message) {
    init {
        ToastUtils.showToast(message)
    }
}