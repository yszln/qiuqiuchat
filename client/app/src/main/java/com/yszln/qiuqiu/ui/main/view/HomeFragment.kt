package com.yszln.qiuqiu.ui.main.view

import com.yszln.lib.fragment.BaseVMFragment
import com.yszln.qiuqiu.R
import com.yszln.qiuqiu.db.CacheDataBase
import com.yszln.qiuqiu.ui.main.adapter.HomeChatAdapter
import com.yszln.qiuqiu.ui.main.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseVMFragment<HomeViewModel>() {

    private val mAdapter=HomeChatAdapter()

    override fun refreshData() {
        CacheDataBase.instance.chatDao().findAll().apply {
            mAdapter.setList(this)
        }
    }

    override fun initView() {
        mRecyclerView.adapter=mAdapter
    }

    override fun observe() {

    }

    override fun layoutId()= R.layout.fragment_home
}