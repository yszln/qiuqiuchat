package com.yszln.qiuqiu.ui.splash

import android.Manifest
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import com.yszln.lib.activity.SuperActivity
import com.yszln.lib.utils.start
import com.yszln.qiuqiu.R
import com.yszln.qiuqiu.db.UserUtils
import com.yszln.qiuqiu.ui.login.view.LoginActivity
import com.yszln.qiuqiu.ui.main.view.MainActivity

class SplashActivity : SuperActivity() {
    override fun layoutId() = R.layout.activity_splash
    override fun initView(savedInstanceState: Bundle?) {
        window.setBackgroundDrawableResource(R.color.white)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            requestPermissions(
                arrayOf(
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ), 0
            )
        }

    }

    override fun onClick(v: View?) {

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (UserUtils.checkLogin()) {
            start(MainActivity::class.java)
        }else{
            start(LoginActivity::class.java)
        }
        finish()
    }

}