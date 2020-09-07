package com.yszln.qiuqiu.ui.main.view

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.viewpager.widget.ViewPager
import com.yszln.lib.activity.BaseActivity
import com.yszln.lib.adapter.BaseFragmentAdapter
import com.yszln.lib.fragment.BaseFragment
import com.yszln.lib.utils.MyStatusBar
import com.yszln.qiuqiu.R
import com.yszln.qiuqiu.service.SocketService
import com.yszln.qiuqiu.ui.main.model.NavBean
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {
    lateinit var mSocketBind: SocketService.MyBinder
    val conn = MyConn()
    var mPagerAdapter = BaseFragmentAdapter<BaseFragment>(supportFragmentManager)


    override fun layoutId() = R.layout.activity_main
    val navs = ArrayList<NavBean>()

    init {
        navs.apply {
            add(NavBean(R.id.main_home, HomeFragment()))
            add(NavBean(R.id.main_mine, MineFragment()))
        }
    }

    override fun initView() {
        MyStatusBar(this)
        val intent = Intent(this, SocketService::class.java)
        startService(intent)
        bindService(intent, conn, Context.BIND_AUTO_CREATE)

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
        mPagerAdapter.addFragment(HomeFragment())
        mPagerAdapter.addFragment(MineFragment())
        mainViewPager.adapter = mPagerAdapter
    }

    override fun onDestroy() {
        unbindService(conn)
        super.onDestroy()
    }

    inner class MyConn : ServiceConnection {
        override fun onServiceDisconnected(p0: ComponentName?) {

        }

        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            mSocketBind = (p1 as SocketService.MyBinder)
        }

    }

}


