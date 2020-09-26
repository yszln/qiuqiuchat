package com.yszln.qiuqiu.ui.main.view

import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.yszln.lib.fragment.BaseVMFragment
import com.yszln.qiuqiu.R
import com.yszln.qiuqiu.ui.main.adapter.LinkmanAdapter
import com.yszln.qiuqiu.ui.main.viewmodel.LinkmanViewModel
import kotlinx.android.synthetic.main.fragment_linkman.*

class LinkmanFragment : BaseVMFragment<LinkmanViewModel>() {

    val mAdapter = LinkmanAdapter()

    override fun refreshData() {
        mViewModel.getLinkmanList()
    }

    override fun initView() {
        searchBarView.titleBlack.visibility=View.GONE
        mRecyclerView.adapter = mAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(context);
    }

    override fun observe() {
        mViewModel.apply {
            mFriends.observe(this@LinkmanFragment, Observer {
                mAdapter.setList(it)
            })
        }
    }

    override fun layoutId() = R.layout.fragment_linkman
}