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
        return null != getLoginUser()
    }

    fun getLoginUser(): TbUser {
        val findFirst = CacheDataBase.instance.userDao().findFirst()

        return findFirst;
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

    fun login(loginer: LoginBean) {
        CacheDataBase.instance.userDao().deleteAll()
        loginer.member.token=loginer.token
        CacheDataBase.instance.userDao().insert(loginer.member)

    }

    fun loginOut() {

        CacheDataBase.instance.userDao().deleteAll()
        start(LoginActivity::class.java)
        //停止消息推送服务
        AppManageHelper.currentActivity().apply {
            stopService(Intent(this, WebSocketService::class.java))
        }

    }

    fun getToken(): String {
        return getLoginUser()?.token?:""
    }


}