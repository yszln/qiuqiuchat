package com.yszln.lib.viewmodel

enum class RefreshStatus {
    //刷新
    REFRESH,
    //刷新完成
    REFRESH_END,
    //刷新失败
    REFRESH_FAIL,
    //加载
    LOAD,
    //加载完成
    LOAD_COMPLETE,
    //加载失败
    LOAD_FAIL,
    //加载结束，不可以再加载
    LOAD_END

}