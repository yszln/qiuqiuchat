package com.yszln.lib.activity

import com.yszln.lib.loading.ILoading

interface IBaseView : ILoading {
    /**
     * 初始化view
     */
     fun initView()

    /**
     * 点击事件
     */
     fun onClick() {}

    /**
     * 设置观察者
     */
     fun observe()
    /**
     * 请求数据
     */
     fun refreshData()
}