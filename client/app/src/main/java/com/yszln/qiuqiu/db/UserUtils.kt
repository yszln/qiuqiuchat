package com.yszln.qiuqiu.db

import android.content.Intent
import com.yszln.lib.app.AppManageHelper
import com.yszln.qiuqiu.R
import com.yszln.qiuqiu.db.table.TbUser
import com.yszln.qiuqiu.service.WebSocketService
import com.yszln.qiuqiu.ui.login.model.LoginBean
import com.yszln.qiuqiu.ui.main.view.MainActivity

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
            toLogin()
            false
        }
    }

    private fun toLogin() {
        while (MainActivity.mNavController.popBackStack()){

        }
        MainActivity.mNavController.navigate(R.id.loginFragment)
    }

    fun login(loginer: LoginBean) {
        CacheDataBase.instance.userDao().deleteAll()
        loginer.member.token=loginer.token
        CacheDataBase.instance.userDao().insert(loginer.member)

    }

    fun loginOut() {

        CacheDataBase.instance.userDao().deleteAll()

        //停止消息推送服务
        AppManageHelper.currentActivity().apply {
            stopService(Intent(this, WebSocketService::class.java))
        }

        //去登录
        toLogin()

    }

    fun getToken(): String {
        return getLoginUser()?.token?:""
    }


}