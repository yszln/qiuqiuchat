package com.yszln.lib.adapter

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.chad.library.adapter.base.module.BaseLoadMoreModule
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder

abstract class CommonMultiItemLoadAdapter<T : MultiItemEntity> :
BaseMultiItemQuickAdapter<T, BaseViewHolder>(), LoadMore, LoadMoreModule {
    override fun clearData() {
        setList(ArrayList())
    }

    override fun getLoadModule(): BaseLoadMoreModule {
        return loadMoreModule
    }
}