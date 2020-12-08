package com.yszln.qiuqiu.ui.main.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yszln.lib.fragment.BaseFragment
import com.yszln.lib.fragment.SuperFragment
import com.yszln.lib.viewmodel.BaseViewModel
import com.yszln.qiuqiu.R
import kotlinx.android.synthetic.main.fragment_setting.*


class SettingFragment : BaseFragment<BaseViewModel>() {
    override fun onClick(v: View?) {
    }

    override fun layoutId()=R.layout.fragment_setting

    override fun initView(savedInstanceState: Bundle?) {
        mTitleBar.mTitleTv.text="设置"
    }

    override fun observer() {

    }

    override fun refreshData() {
    }

}