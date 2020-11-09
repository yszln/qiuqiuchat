package com.yszln.lib.ibase

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

interface IBaseVM : SwipeRefreshLayout.OnRefreshListener {


    /**
     * 初始化liveData观察者
     */
     fun observer()
    /**
     * 开始刷新
     */
    fun refreshData()


    /**
     * 显示loading
     */
    fun showLoading()

    /**
     * 关闭loading
     */
    fun dismissLoading()

    override fun onRefresh() {
        refreshData()
    }

    /**
     * 是否为空数据
     */
    fun isEmptyData() = false

}