package com.yszln.lib.utils

import android.util.Log

object LogUtil {

    fun e(str: String) {
        Log.e("ERROR",str)
    }
    @JvmStatic
    fun e(tag:String,str: String){
        Log.e(tag,str)
    }

}