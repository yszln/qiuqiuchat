package com.yszln.qiuqiu.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import com.yszln.lib.utils.LogUtil
import com.yszln.lib.utils.toJson
import com.yszln.lib.viewmodel.BaseViewModel
import com.yszln.qiuqiu.db.CacheDataBase
import com.yszln.qiuqiu.db.table.TbChat

class HomeViewModel : BaseViewModel() {

    val chants= MutableLiveData<MutableList<TbChat>>();

    fun  getData(){
        launch(
            {
                chants.value=  CacheDataBase.instance.chatDao().findAll()
                LogUtil.e(chants.value?.toJson()?:"")
                refreshEnd()
            }
        )
    }
}