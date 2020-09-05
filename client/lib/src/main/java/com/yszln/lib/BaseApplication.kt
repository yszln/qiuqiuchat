package com.yszln.lib

import android.app.Application
import android.content.Context
import com.chad.library.adapter.base.module.LoadMoreModuleConfig
import com.yszln.lib.widget.CustomLoadMoreView


open class BaseApplication : Application() {


    companion object {
        var isDebug = BuildConfig.DEBUG
        lateinit var mContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        mContext = this
        // 在 Application 中配置全局自定义的 LoadMoreView
        LoadMoreModuleConfig.defLoadMoreView = CustomLoadMoreView()
    }
}