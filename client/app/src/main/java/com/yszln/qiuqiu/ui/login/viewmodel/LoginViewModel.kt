package com.yszln.qiuqiu.ui.login.viewmodel

import androidx.lifecycle.MutableLiveData
import com.yszln.lib.utils.getString
import com.yszln.lib.utils.toast
import com.yszln.lib.viewmodel.BaseViewModel
import com.yszln.qiuqiu.R
import com.yszln.qiuqiu.api.Api
import com.yszln.qiuqiu.ui.login.model.LoginBean

class LoginViewModel : BaseViewModel() {

    val loginUser = MutableLiveData<LoginBean>()

    fun login(username: String, password: String) {
        launch(
            block = {
                loginUser.value = Api.mApiServer.login(username, password).data()
                getString(R.string.login_success).toast()
    }
        )
    }

    fun register(username: String, password: String) {
        launch(
            block = {
                loginUser.value = Api.mApiServer.register(username, password).data()
                getString(R.string.register_success).toast()
            }
        )
    }

}