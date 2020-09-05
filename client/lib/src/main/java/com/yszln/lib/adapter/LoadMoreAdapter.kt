package com.yszln.lib.adapter

import com.chad.library.adapter.base.module.BaseLoadMoreModule
import com.chad.library.adapter.base.module.LoadMoreModule

/**
* @author: yszln
* @date: 2020/8/7 21:08
* @description: 加载更多适配器
* @history:
*/
abstract class LoadMoreAdapter<T>(layoutResId: Int) :CommonAdapter<T>(layoutResId),LoadMoreModule,LoadMore{


    override fun getLoadModule(): BaseLoadMoreModule {
        return loadMoreModule
    }


}