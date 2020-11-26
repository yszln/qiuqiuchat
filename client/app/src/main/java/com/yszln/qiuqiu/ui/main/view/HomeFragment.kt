package com.yszln.qiuqiu.ui.main.view

import android.os.Bundle
import android.view.View
import com.yszln.lib.fragment.BaseFragment
import com.yszln.lib.utils.LogUtil
import com.yszln.lib.utils.start
import com.yszln.lib.utils.toJson
import com.yszln.qiuqiu.R
import com.yszln.qiuqiu.db.CacheDataBase
import com.yszln.qiuqiu.ui.main.adapter.HomeChatAdapter
import com.yszln.qiuqiu.ui.main.viewmodel.HomeViewModel
import com.yszln.qiuqiu.ui.search.view.SearchActivity
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment<HomeViewModel>() {

    private val mAdapter = HomeChatAdapter()

    override fun refreshData() {
        CacheDataBase.instance.chatDao().findAll().apply {
            LogUtil.e(toJson())
            mAdapter.addData(this)
        }
    }

    override fun registerClick(): MutableList<View> {
        return mutableListOf(search_rl)
    }


    override fun onClick(v: View?) {
        when(v?.id){
            search_rl.id->{
                start(SearchActivity::class.java)
            }
        }
    }

    override fun layoutId() = R.layout.fragment_home
    override fun initView(savedInstanceState: Bundle?) {
        mRecyclerView.adapter = mAdapter
    }

    override fun observer() {
    }
}