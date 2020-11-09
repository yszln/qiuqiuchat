package com.yszln.lib.adapter

import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yszln.lib.loading.EmptyView

abstract class CommonAdapter<T>(layoutResId: Int) :
    BaseQuickAdapter<T, BaseViewHolder>(layoutResId) {

    init {
        registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                super.onChanged()
                checkData()
            }
        })
    }


    private fun checkData() {
        if (data.isEmpty()) {
            setEmptyView(EmptyView(context))
        }
    }


}