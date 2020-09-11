package com.yszln.qiuqiu.db

import android.content.Intent
import android.text.TextUtils
import com.yszln.lib.app.AppManageHelper
import com.yszln.lib.utils.SPUtils
import com.yszln.lib.utils.start
import com.yszln.qiuqiu.MyApp
import com.yszln.qiuqiu.db.table.TbUser
import com.yszln.qiuqiu.service.WebSocketService
import com.yszln.qiuqiu.ui.login.model.LoginBean
import com.yszln.qiuqiu.ui.login.view.LoginActivity
import com.yszln.qiuqiu.utils.Constant

object UserUtils {

    fun isLogin(): Boolean {
        return null != getLoginUser() && !TextUtils.isEmpty(getToken())
    }

    fun getLoginUser(): TbUser {
        return CacheDataBase.instance.userDao().findFirst();
    }

    fun checkLogin(): Boolean {
        return if (isLogin()) {
            true
        } else {
            //去登录
            start(LoginActivity::class.java)
            false
        }
    }

    fun login(user: LoginBean) {
        CacheDataBase.instance.userDao().deleteAll()
        CacheDataBase.instance.userDao().insert(user.member)
        setToken(user.token)
    }

    fun loginOut() {

        CacheDataBase.instance.userDao().deleteAll()
        setToken("")
        start(LoginActivity::class.java)
        //停止消息推送服务
        AppManageHelper.currentActivity().apply {
            stopService(Intent(this, WebSocketService::class.java))
        }

    }

    fun getToken(): String {
        return SPUtils.get(Constant.TOKEN) ?: ""
    }

    fun setToken(token: String) {
        SPUtils.put(Constant.TOKEN, token)
    }
}