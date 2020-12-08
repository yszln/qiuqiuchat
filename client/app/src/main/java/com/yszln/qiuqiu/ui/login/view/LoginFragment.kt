package com.yszln.qiuqiu.ui.login.view

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.yszln.lib.fragment.BaseFragment
import com.yszln.lib.utils.textStr
import com.yszln.lib.utils.toast
import com.yszln.qiuqiu.R
import com.yszln.qiuqiu.db.UserUtils
import com.yszln.qiuqiu.ui.login.viewmodel.LoginViewModel
import com.yszln.qiuqiu.utils.Constant
import com.yszln.qiuqiu.utils.TextWatchUtils
import kotlinx.android.synthetic.main.activity_login.*

/**
 * 登陆
 */
class LoginFragment : BaseFragment<LoginViewModel>() {

    var loginType = 0;


    override fun layoutId() = R.layout.activity_login


    override fun initView(savedInstanceState: Bundle?) {
        loginType = arguments?.getInt(Constant.LOGIN_TYPE) ?: 0;
        setLayoutWithType()
    }

    override fun onBackPressed()=false
    override fun onClick(v: View?) {
        when (v?.id) {
            loginClose.id -> {
                mActivity.finish()
            }
            loginBtn.id -> {
                submitData()
            }
            loginForgetPassword.id -> {
                onBackPressed()
                findNavController().navigate(
                    R.id.action_loginFragment_to_register,
                    Bundle().apply { putInt(Constant.LOGIN_TYPE, 2) })
            }
            loginRegister.id -> {
                onBackPressed()
                findNavController().navigate(
                    R.id.action_loginFragment_to_register,
                    Bundle().apply { putInt(Constant.LOGIN_TYPE, 1) })
            }
        }
    }

    override fun observer() {
        mViewModel.apply {
            loginUser.observe(this@LoginFragment, Observer {
                //缓存
                UserUtils.login(it)
                findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
            })
        }
    }

    override fun refreshData() {

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

    override fun registerClick(): MutableList<View> {
        return mutableListOf(
            loginClose,
            loginBtn,
            loginForgetPassword,
            loginRegister,
            loginFreeze,
            loginSecurityCenter
        )
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