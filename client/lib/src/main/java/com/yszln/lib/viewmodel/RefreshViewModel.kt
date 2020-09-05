package com.yszln.lib.viewmodel

import androidx.lifecycle.MutableLiveData

open class RefreshViewModel: BaseViewModel() {

    /**
     * 刷新状态
     */
    var mRefreshStatus=MutableLiveData<Int>()


    open fun refreshComplete(){
        mRefreshStatus.value=System.currentTimeMillis().toInt()
    }

}