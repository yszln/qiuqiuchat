package com.yszln.qiuqiu.ui.main.view

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.yszln.lib.activity.SuperActivity
import com.yszln.lib.adapter.BaseFragmentAdapter
import com.yszln.lib.fragment.SuperFragment
import com.yszln.lib.utils.MyStatusBar
import com.yszln.qiuqiu.R
import com.yszln.qiuqiu.service.WebSocketService
import com.yszln.qiuqiu.ui.main.model.NavBean
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : SuperActivity() {


    var mPagerAdapter = BaseFragmentAdapter<SuperFragment>(supportFragmentManager)


    override fun layoutId() = R.layout.activity_main
    override fun initView(savedInstanceState: Bundle?) {
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

    override fun onClick(v: View?) {
    }

    val navs = ArrayList<NavBean>()

    init {
        navs.apply {
            add(NavBean(R.id.main_home, HomeFragment()))
            add(NavBean(R.id.main_linkman, LinkmanFragment()))
            add(NavBean(R.id.main_mine, MineFragment()))
        }
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.addCategory(Intent.CATEGORY_HOME)
            startActivity(intent)
            return true
        }
        return super.onKeyDown(keyCode, event)
    }


}


