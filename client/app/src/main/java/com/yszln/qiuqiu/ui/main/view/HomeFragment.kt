package com.yszln.qiuqiu.ui.main.view

import com.yszln.lib.fragment.BaseVMFragment
import com.yszln.lib.utils.LogUtil
import com.yszln.lib.utils.StatusBarUtil
import com.yszln.lib.utils.start
import com.yszln.lib.utils.toJson
import com.yszln.qiuqiu.R
import com.yszln.qiuqiu.db.CacheDataBase
import com.yszln.qiuqiu.ui.main.adapter.HomeChatAdapter
import com.yszln.qiuqiu.ui.main.viewmodel.HomeViewModel
import com.yszln.qiuqiu.ui.search.view.SearchActivity
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseVMFragment<HomeViewModel>() {

    private val mAdapter = HomeChatAdapter()

    override fun refreshData() {
        CacheDataBase.instance.chatDao().findAll().apply {
            LogUtil.e(toJson())
            mAdapter.addData(this)
        }
    }

    override fun initView() {
        mRecyclerView.adapter = mAdapter
    }

    override fun observe() {

    }

    override fun onClick() {
        search_rl.setOnClickListener {
            start(SearchActivity::class.java)
        }
    }
    override fun layoutId() = R.layout.fragment_home
}