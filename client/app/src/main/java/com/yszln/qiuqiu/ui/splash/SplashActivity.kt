package com.yszln.qiuqiu.ui.splash

import android.os.Handler
import android.os.Looper
import com.yszln.lib.activity.BaseActivity
import com.yszln.lib.utils.start
import com.yszln.qiuqiu.R
import com.yszln.qiuqiu.db.UserUtils
import com.yszln.qiuqiu.ui.main.view.MainActivity

class SplashActivity : BaseActivity() {
    override fun layoutId() = R.layout.activity_splash

    override fun initView() {
        window.setBackgroundDrawableResource(R.color.white)
        Handler(Looper.getMainLooper()).postDelayed({
            if (UserUtils.checkLogin()) {
                start(MainActivity::class.java)
            }
        },2000)

    }

}