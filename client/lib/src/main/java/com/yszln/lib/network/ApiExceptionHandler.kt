package com.yszln.lib.network

object ApiExceptionHandler {
    var mApiExceptionListener: ApiExceptionListener? = null


   open interface ApiExceptionListener {
        fun onError(code: Int, message: String)
    }
}
