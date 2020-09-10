package com.yszln.qiuqiu.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import com.yszln.lib.viewmodel.RefreshViewModel
import com.yszln.qiuqiu.api.Api
import com.yszln.qiuqiu.db.table.TbUser

class LinkmanViewModel : RefreshViewModel() {

    val mFriends = MutableLiveData<MutableList<TbUser>>()

    fun getLinkmanList() {
        launch(
            block = {
                mFriends.value = Api.mApiServer.getFriends().data()
            }
        )
    }

}