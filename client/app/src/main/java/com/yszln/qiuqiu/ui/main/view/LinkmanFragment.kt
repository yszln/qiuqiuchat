package com.yszln.qiuqiu.ui.main.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.yszln.lib.fragment.BaseFragment
import com.yszln.qiuqiu.R
import com.yszln.qiuqiu.ui.main.adapter.LinkmanAdapter
import com.yszln.qiuqiu.ui.main.viewmodel.LinkmanViewModel
import kotlinx.android.synthetic.main.fragment_linkman.*

class LinkmanFragment : BaseFragment<LinkmanViewModel>() {

    val mAdapter = LinkmanAdapter()

    override fun refreshData() {
        mViewModel.getLinkmanList()
    }





    override fun onClick(v: View?) {
    }

    override fun layoutId() = R.layout.fragment_linkman
    override fun initView(savedInstanceState: Bundle?) {
        searchBarView.titleBlack.visibility=View.GONE
        mRecyclerView.adapter = mAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    override fun observer() {
        mViewModel.apply {
            mFriends.observe(this@LinkmanFragment, Observer {
                mAdapter.setList(it)
            })
        }
    }
}