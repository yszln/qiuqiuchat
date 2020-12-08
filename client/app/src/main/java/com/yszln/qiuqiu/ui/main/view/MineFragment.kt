package com.yszln.qiuqiu.ui.main.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.yszln.lib.fragment.BaseFragment
import com.yszln.lib.utils.load
import com.yszln.qiuqiu.R
import com.yszln.qiuqiu.db.UserUtils
import com.yszln.qiuqiu.service.WebSocketService
import com.yszln.qiuqiu.ui.main.viewmodel.MineViewModel
import kotlinx.android.synthetic.main.fragment_mine.*

class MineFragment : BaseFragment<MineViewModel>() {
    override fun onClick(v: View?) {
        when(v?.id){
            mineLoginOutTv.id->{
                UserUtils.loginOut()
            }
            mineSettingIv.id->{
                findNavController().navigate(R.id.settingFragment)
            }
        }
    }

    override fun layoutId() = R.layout.fragment_mine
    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun registerClick(): MutableList<View> {
        return mutableListOf(mineLoginOutTv,mineSettingIv)
    }

    override fun observer() {
    }

    override fun refreshData() {
        UserUtils.getLoginUser()?.apply {
            mineAvatarIv.load(avatar)
            mineNameTv.text = username
        }
        WebSocketService.isConn.observe(this, Observer {
            mineStatusTv.text = if (it) "在线" else "离线"
        })
    }


}