package com.yszln.qiuqiu.ui.main.view

import android.content.Intent
import androidx.viewpager.widget.ViewPager
import com.yszln.lib.activity.BaseActivity
import com.yszln.lib.adapter.BaseFragmentAdapter
import com.yszln.lib.fragment.BaseFragment
import com.yszln.lib.utils.MyStatusBar
import com.yszln.qiuqiu.R
import com.yszln.qiuqiu.service.WebSocketService
import com.yszln.qiuqiu.ui.main.model.NavBean
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {


    var mPagerAdapter = BaseFragmentAdapter<BaseFragment>(supportFragmentManager)


    override fun layoutId() = R.layout.activity_main
    val navs = ArrayList<NavBean>()

    init {
        navs.apply {
            add(NavBean(R.id.main_home, HomeFragment()))
            add(NavBean(R.id.main_linkman, LinkmanFragment()))
            add(NavBean(R.id.main_mine, MineFragment()))
        }
    }

    override fun initView() {
        MyStatusBar(this)
        val intent = Intent(this, WebSocketService::class.java)
        startService(intent)



        mainNav.setOnNavigationItemSelectedListener {
            for (i in 0 until navs.size) {
                if (it.itemId == navs[i].id) {
                    mainViewPager.currentItem = i
                    true
                }
            }
            true
        }
        mainViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {

            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
        for (nav in navs) {
            mPagerAdapter.addFragment(nav.fragment)
        }
        mainViewPager.adapter = mPagerAdapter
    }


}


