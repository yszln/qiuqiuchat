package com.yszln.lib.network

import com.google.gson.Gson
import com.yszln.lib.utils.LogUtil
import com.yszln.lib.utils.SPUtils
import com.yszln.lib.utils.toJson
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

/**
 * @author: yszln
 * @date: 2020/8/16 20:58
 * @description: cookie处理，
 * @history:
 */
class CookieJarImpl : CookieJar {

    val mCookies = ArrayList<Cookie>()

    init {
        var oldCookie =
            Gson().fromJson<CookieBean>(
                SPUtils.get("COOKIES"),
                CookieBean::class.java
            )?.list?: mutableListOf<Cookie>()
        mCookies.addAll(oldCookie)
    }



    override fun loadForRequest(url: HttpUrl): MutableList<Cookie> {

        //过期的Cookie
        val invalidCookies: MutableList<Cookie> = ArrayList()
        //有效的Cookie
        val validCookies: MutableList<Cookie> = ArrayList()

        for (cookie in mCookies) {
            if (cookie.expiresAt < System.currentTimeMillis()) {
                //过期
                invalidCookies.add(cookie)
            } else if (cookie.matches(url)) {
                //匹配url
                validCookies.add(cookie)
            }
        }

        mCookies.removeAll(invalidCookies)

        LogUtil.e("加载cookie->${validCookies.toJson()}")
        return mCookies
    }

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        mCookies.addAll(cookies)
        SPUtils.put(
            "COOKIES", CookieBean(mCookies).toJson()
        )
    }

    data class CookieBean(val list: MutableList<Cookie>) {
    }
}