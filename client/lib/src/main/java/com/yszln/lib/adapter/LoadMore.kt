package com.yszln.lib.adapter

import com.chad.library.adapter.base.module.BaseLoadMoreModule

interface LoadMore {

     fun clearData()

    fun getLoadModule(): BaseLoadMoreModule

    fun getItemCount():Int
}