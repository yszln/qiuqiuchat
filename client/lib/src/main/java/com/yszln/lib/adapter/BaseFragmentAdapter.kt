package com.yszln.lib.adapter

import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.yszln.lib.fragment.BaseFragment


class BaseFragmentAdapter<F : BaseFragment>(
    fm: FragmentManager,
    behavior: Int = BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) :
    FragmentPagerAdapter(fm, behavior) {

    /** Fragment 集合  */
    private val mFragmentSet: MutableList<F> = ArrayList<F>()

    /** Fragment 标题  */
    private val mFragmentTitle: MutableList<CharSequence> = ArrayList<CharSequence>()

    /** 当前显示的Fragment  */
    private var mShowFragment: F? = null

    /** 当前 ViewPager  */
    private var mViewPager: ViewPager? = null

    /** 设置成懒加载模式  */
    private var mLazyMode = true


    override fun getItem(position: Int): F {
        return mFragmentSet[position]
    }

    override fun getCount(): Int {
        return mFragmentSet.size
    }

    @Nullable
    override fun getPageTitle(position: Int): CharSequence? {
        return mFragmentTitle[position]
    }

    /**
     * 设置当前显示的页面
     *
     * @param clazz 页面class
     */
    fun getClassPosition(clazz: Class<out Fragment>): Int {
        for (i in 0 until mFragmentSet.size) {
            if (mFragmentSet[i].javaClass.name == clazz.name) {
                return i
            }
        }
        //没有的话统一选择第一个
        return 0
    }


    override fun setPrimaryItem(container: ViewGroup, position: Int, `object`: Any) {
        if (`object` != getShowFragment()) {
            // 记录当前的Fragment对象
            mShowFragment = `object` as F
        }
        super.setPrimaryItem(container, position, `object`)
    }

    /**
     * 添加 Fragment
     */
    fun addFragment(fragment: F) {
        addFragment(fragment, "")
    }

    fun addFragment(fragment: F, title: CharSequence) {
        mFragmentSet.add(fragment)
        mFragmentTitle.add(title)

        if (mViewPager != null) {
            notifyDataSetChanged()
            if (mLazyMode) {
                mViewPager?.offscreenPageLimit = count
            }
        }
    }

    /**
     * 获取当前的Fragment
     */
    open fun getShowFragment(): F? {
        return mShowFragment
    }

    override fun startUpdate(container: ViewGroup) {
        super.startUpdate(container)
        if (container is ViewPager) {
            // 记录绑定 ViewPager
            mViewPager = container
            refreshLazyMode()
        }
    }

    /**
     * 设置懒加载模式
     */
    fun setLazyMode(lazy: Boolean) {
        mLazyMode = lazy
        refreshLazyMode()
    }

    /**
     * 刷新加载模式
     */
    private fun refreshLazyMode() {
        if (mViewPager == null) {
            return
        }
        if (mLazyMode) {
            // 设置成懒加载模式（也就是不限制 Fragment 展示的数量）
            mViewPager?.offscreenPageLimit = count
        } else {
            mViewPager?.setOffscreenPageLimit(1)
        }
    }
}