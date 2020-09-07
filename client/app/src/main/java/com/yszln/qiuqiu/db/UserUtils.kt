package com.yszln.qiuqiu.db

import android.text.TextUtils
import com.yszln.lib.utils.SPUtils
import com.yszln.qiuqiu.db.table.TbUser
import com.yszln.qiuqiu.ui.login.model.LoginBean
import com.yszln.qiuqiu.utils.Constant

object UserUtils {

    fun isLogin(): Boolean {
        return null != getLoginUser()&&!TextUtils.isEmpty(getToken())
    }

    fun getLoginUser(): TbUser {
        return CacheDataBase.instance.userDao().findFirst();
    }

    fun checkLogin(): Boolean {
        return if (isLogin()) {
            true
        } else {
            //去登录
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
    }

    fun getToken(): String {
        return SPUtils.get(Constant.TOKEN) ?: ""
    }

    fun setToken(token: String) {
        SPUtils.put(Constant.TOKEN, token)
    }
}