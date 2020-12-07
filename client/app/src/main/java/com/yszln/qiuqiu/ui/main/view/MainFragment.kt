package com.yszln.qiuqiu.ui.main.view

import android.os.Bundle
import android.view.View
import com.yszln.lib.fragment.BaseFragment
import com.yszln.lib.viewmodel.BaseViewModel
import com.yszln.qiuqiu.R
import com.yszln.qiuqiu.ui.main.model.NavBean
import kotlinx.android.synthetic.main.fragment_main.*


class MainFragment : BaseFragment<BaseViewModel>() {


    val navs = ArrayList<NavBean>();

    override fun onClick(v: View?) {

    }

    override fun layoutId() = R.layout.fragment_main

    private fun switchFragment(index: Int) {
        val transaction = childFragmentManager.beginTransaction()
        for (nav in navs) {
            transaction.hide(nav.fragment) //隐藏上个Fragment
        }
        if (!navs[index].fragment.isAdded) {
            transaction.add(R.id.mainContainer, navs[index].fragment)
        }
        transaction.show(navs[index].fragment).commit()

    }

    override fun onBackPressed() = false


    override fun initView(savedInstanceState: Bundle?) {
        navs.apply {
            add(NavBean(R.id.main_home, HomeFragment()))
            add(NavBean(R.id.main_linkman, LinkmanFragment()))
            add(NavBean(R.id.main_mine, MineFragment()))
        }
        switchFragment(0)
        mainNav.setOnNavigationItemSelectedListener {
            for (i in 0 until navs.size) {
                if (it.itemId == navs[i].id) {
                    switchFragment(i);
                    true
                }
            }
            true
        }
    }

    override fun observer() {
    }

    override fun refreshData() {
    }
}