package com.yszln.lib.ibase

import android.os.Bundle
import android.view.View

interface IBaseView {

    /**
     * 布局id
     */
    fun layoutId(): Int

    /**
     * 初始化view
     */
    fun initView(savedInstanceState: Bundle?)

    /**
     * 初始化点击事件
     */

    fun registerClick(): MutableList<View> = mutableListOf<View>()

    /**
     * 初始化intent数据
     */
    fun initIntentData() {

    }

}