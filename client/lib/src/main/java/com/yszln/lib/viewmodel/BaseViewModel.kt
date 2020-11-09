package com.yszln.lib.viewmodel

import androidx.lifecycle.MutableLiveData

open class BaseViewModel: SuperViewModel() {

    protected var page = 0
    /**
     * 刷新状态
     */
    var mRefreshStatus=MutableLiveData<RefreshStatus>()



    open fun refreshEnd(){
        mRefreshStatus.value=RefreshStatus.REFRESH_END
    }
    open fun refreshError(){
        mRefreshStatus.value=RefreshStatus.REFRESH_FAIL
    }

    open fun loadEnd(){
        mRefreshStatus.value=RefreshStatus.LOAD_END
    }

    open fun loadError(){
        mRefreshStatus.value=RefreshStatus.LOAD_FAIL
    }

    open fun loadComplete(){
        mRefreshStatus.value=RefreshStatus.LOAD_COMPLETE
    }

}