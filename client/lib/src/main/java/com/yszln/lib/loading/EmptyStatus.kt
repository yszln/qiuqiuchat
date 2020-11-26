package com.yszln.lib.loading

import com.yszln.lib.R
import com.yszln.lib.utils.getString

enum class EmptyStatus(val text: String,val icon: Int) {
    //默认状态，加载
    LOADING(getString(R.string.loading_data_content),R.drawable.loading_progress),

    //没有数据
    EMPTY(getString(R.string.loading_data_empty_content),R.mipmap.ic_empty_data),

    //错误
    ERROR(getString(R.string.web_loading_error),R.mipmap.ic_empty_data),

    //网络异常
    NO_NET(getString(R.string.loading_data_network_error),R.mipmap.ic_empty_data),

    //成功
    SUCCESS("",0),
}
