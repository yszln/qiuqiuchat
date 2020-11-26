package com.yszln.lib.loading

import android.content.Context
import android.net.ConnectivityManager
import com.yszln.lib.app.BaseApplication

object NetworkUtil {
    /**
     * 检测网络是否可用
     *
     * @param context 上下文
     * @return true 可用
     * 有问题
     */
    fun isNetWorkConnected(): Boolean {
        val mConnectivityManager =
            BaseApplication.instance.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val mNetworkInfo = mConnectivityManager.activeNetworkInfo
        if (mNetworkInfo != null) {
            return mNetworkInfo.isAvailable && mNetworkInfo.isConnected
        }
        return false
    }
}
