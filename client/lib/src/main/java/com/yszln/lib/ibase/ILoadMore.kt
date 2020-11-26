package com.yszln.lib.ibase

import com.chad.library.adapter.base.listener.OnLoadMoreListener
import com.chad.library.adapter.base.module.BaseLoadMoreModule

interface ILoadMore : OnLoadMoreListener {


    /**
     * 开始加载
     */
    fun loadData() {}

    /**
     * 加载更多
     */
    fun getLoadModule(): BaseLoadMoreModule? = null


    override fun onLoadMore() {
        loadData()
    }
}