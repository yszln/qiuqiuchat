package com.yszln.lib.adapter

import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.yszln.lib.loading.EmptyView

abstract class CommonAdapter<T>(layoutResId: Int) :
    BaseQuickAdapter<T, CommonViewHolder>(layoutResId) {

    init {
        registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                super.onChanged()
                checkData()
            }
        })
    }


    private fun checkData() {
        if (data == null || data.isEmpty()) {
            setEmptyView(EmptyView(context))
        }
    }

    fun clearData() {
        setList(null)
    }

}