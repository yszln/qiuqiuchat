package com.yszln.qiuqiu.ui.main.view

import androidx.lifecycle.Observer
import com.yszln.lib.fragment.BaseVMFragment
import com.yszln.lib.utils.load
import com.yszln.qiuqiu.R
import com.yszln.qiuqiu.db.UserUtils
import com.yszln.qiuqiu.service.WebSocketService
import com.yszln.qiuqiu.ui.main.viewmodel.MineViewModel
import kotlinx.android.synthetic.main.fragment_mine.*

class MineFragment : BaseVMFragment<MineViewModel>() {
    override fun layoutId() = R.layout.fragment_mine

    override fun refreshData() {
    }

    override fun initView() {
        UserUtils.getLoginUser()?.apply {
            mineAvatarIv.load(avatar)
            mineNameTv.text = username
        }
        WebSocketService.isConn.observe(this, Observer {
            mineStatusTv.text = if (it) "在线" else "离线"
        })

    }

    override fun observe() {
    }
}