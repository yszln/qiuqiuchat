package com.yszln.qiuqiu.ui.main.view

import com.yszln.lib.fragment.BaseFragment
import com.yszln.lib.fragment.BaseVMFragment
import com.yszln.qiuqiu.R
import com.yszln.qiuqiu.db.CacheDataBase
import com.yszln.qiuqiu.db.UserUtils
import com.yszln.qiuqiu.ui.main.viewmodel.MineViewModel
import kotlinx.android.synthetic.main.fragment_mine.*

class MineFragment : BaseVMFragment<MineViewModel>() {
    override fun layoutId()= R.layout.fragment_mine

    override fun refreshData() {
    }

    override fun initView() {
        mineLoginName.text=UserUtils.getLoginUser().username
    }

    override fun observe() {
    }
}