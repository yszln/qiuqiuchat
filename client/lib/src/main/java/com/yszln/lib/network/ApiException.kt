package com.yszln.lib.network

import com.yszln.lib.utils.LogUtil
import com.yszln.lib.utils.ToastUtils

/**
 * @author: yszln
 * @date: 2020/8/7 21:13
 * @description: 自定义Api错误
 * @history:
 */
class ApiException(val code: Int, message: String) : Exception(message) {


    init {
        LogUtil.e("apiException:$message")
        ToastUtils.showToast(message)
        ApiExceptionHandler.mApiExceptionListener?.onError(code, message)
    }


}
