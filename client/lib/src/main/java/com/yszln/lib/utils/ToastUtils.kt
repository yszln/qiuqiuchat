package com.yszln.lib.utils

import android.widget.Toast
import com.yszln.lib.BaseApplication


object ToastUtils {

    private var mToast: Toast? = null
    private const val duration: Int = 2000;

    fun showToast(
        message: CharSequence
    ) {
        mToast?.cancel()
        mToast = Toast.makeText(BaseApplication.mContext, message, Toast.LENGTH_SHORT)
        mToast?.show()


    }
}