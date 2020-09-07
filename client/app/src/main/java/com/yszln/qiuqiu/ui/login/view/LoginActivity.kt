package com.yszln.qiuqiu.ui.login.view

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.Observer
import com.yszln.lib.activity.BaseVMActivity
import com.yszln.lib.utils.start
import com.yszln.lib.utils.textStr
import com.yszln.lib.utils.toast
import com.yszln.qiuqiu.R
import com.yszln.qiuqiu.db.UserUtils
import com.yszln.qiuqiu.ui.main.view.MainActivity
import com.yszln.qiuqiu.ui.login.viewmodel.LoginViewModel
import com.yszln.qiuqiu.utils.Constant
import com.yszln.qiuqiu.utils.TextWatchUtils
import kotlinx.android.synthetic.main.activity_login.*

/**
 * 登陆
 */
class LoginActivity : BaseVMActivity<LoginViewModel>() {

    var loginType = 0;

    override fun refreshData() {
    }


    override fun layoutId() = R.layout.activity_login

    override fun initView() {
        loginType = intent?.extras?.getInt(Constant.LOGIN_TYPE) ?: 0;
        setLayoutWithType()
    }

    override fun observe() {
        mViewModel.apply {
            loginUser.observe(this@LoginActivity, Observer {
                //缓存
                UserUtils.login(it)
                start(MainActivity::class.java)
            })
        }
    }

    private fun setLayoutWithType() {
        when (loginType) {
            0 -> {
                //登录界面
                loginLayout()
            }
            1 -> {
                // 注册界面
                registerLayout()
            }
            2 -> {
                //忘记密码界面
                forgetPasswordLayout()
            }
        }
    }

    private fun forgetPasswordLayout() {

    }

    /**
     * 登录界面
     */
    private fun loginLayout() {
        loginTitle.text = getString(R.string.login_qiuqiu)
        TextWatchUtils().apply {
            add(userNameEt)
            add(passwordEt)
        }.isEnable.observe(this, Observer {
            loginBtn.isEnabled = it
        })
    }

    /**
     * 注册界面
     */
    private fun registerLayout() {
        loginTitle.text = getString(R.string.regsiter_qiuqiu)
        passwordAgainInput.visibility = View.VISIBLE
        loginRegister.visibility = View.GONE
        TextWatchUtils().apply {
            add(userNameEt)
            add(passwordEt)
            add(passwordAgainEt)
        }.isEnable.observe(this, Observer {
            loginBtn.isEnabled = it
        })
    }


    override fun onClick() {
        loginClose.setOnClickListener {
            finish()
        }
        loginBtn.setOnClickListener {
            submitData()
        }
        loginForgetPassword.setOnClickListener {
            start(LoginActivity::class.java,
                Bundle().apply { putInt(Constant.LOGIN_TYPE, 2) })
        }
        loginRegister.setOnClickListener {
            start(LoginActivity::class.java,
                Bundle().apply { putInt(Constant.LOGIN_TYPE, 1) })
        }
        loginFreeze.setOnClickListener { }
        loginSecurityCenter.setOnClickListener { }
    }

    private fun submitData() {
        when (loginType) {
            0 -> {
                //登录界面
                loginData()
            }
            1 -> {
                // 注册界面
                registerData();
            }
            2 -> {
                //忘记密码界面

            }
        }

    }

    /**
     * 注册
     */
    private fun registerData() {
        var username = userNameEt.textStr()
        var password = passwordEt.textStr()
        var passwordAgain = passwordAgainEt.textStr()
        if (TextUtils.isEmpty(username)) {
            getString(R.string.please_enter_username).toast()
            return
        }
        if (TextUtils.isEmpty(password)) {
            getString(R.string.please_enter_password).toast()
            return
        }
        if (TextUtils.isEmpty(passwordAgain)) {
            getString(R.string.please_enter_password_again).toast()
            return
        }
        mViewModel.register(username, password)
    }

    /**
     * 登录
     */
    private fun loginData() {
        var username = userNameEt.textStr()
        var password = passwordEt.textStr()
        if (TextUtils.isEmpty(username)) {
            getString(R.string.please_enter_username).toast()
            return
        }
        if (TextUtils.isEmpty(password)) {
            getString(R.string.please_enter_password).toast()
            return
        }
        mViewModel.login(username, password)
    }


}