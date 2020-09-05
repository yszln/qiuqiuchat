package com.yszln.lib.adapter

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.chad.library.adapter.base.module.BaseLoadMoreModule
import com.chad.library.adapter.base.module.LoadMoreModule

abstract class CommonMultiItemLoadAdapter<T> :
BaseMultiItemQuickAdapter<MultiItemEntity, CommonViewHolder>(), LoadMore, LoadMoreModule {
    override fun clearData() {
        setList(ArrayList())
    }

    override fun getLoadModule(): BaseLoadMoreModule {
        return loadMoreModule
    }
}