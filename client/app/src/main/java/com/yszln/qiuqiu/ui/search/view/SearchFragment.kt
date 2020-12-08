package com.yszln.qiuqiu.ui.search.view

import android.os.Bundle
import android.view.View
import com.yszln.lib.activity.BaseActivity
import com.yszln.lib.fragment.BaseFragment
import com.yszln.qiuqiu.R
import com.yszln.qiuqiu.ui.search.viewmodel.SearchViewModel


class SearchFragment : BaseFragment<SearchViewModel>() {

    override fun layoutId()=R.layout.activity_search
    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun onClick(v: View?) {
    }


    override fun observer() {
    }

    override fun refreshData() {

    }


}