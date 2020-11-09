package com.yszln.qiuqiu.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import com.yszln.lib.viewmodel.BaseViewModel
import com.yszln.qiuqiu.api.Api
import com.yszln.qiuqiu.db.CacheDataBase
import com.yszln.qiuqiu.db.table.TbFriend

class LinkmanViewModel : BaseViewModel() {

    val mFriends = MutableLiveData<MutableList<TbFriend>>()

    fun getLinkmanList() {
        mFriends.value = CacheDataBase.instance.friendDao().findAll()
        launch(
            block = {
                mFriends.value = Api.mApiServer.getFriends().data()
                CacheDataBase.instance.friendDao().deleteAll()
                mFriends.value?.let { CacheDataBase.instance.friendDao().insert(it) }

            }
        )
    }

}