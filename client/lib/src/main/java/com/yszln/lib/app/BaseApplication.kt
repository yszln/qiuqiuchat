package com.yszln.lib.app

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.chad.library.adapter.base.module.LoadMoreModuleConfig
import com.yszln.lib.BuildConfig
import com.yszln.lib.widget.CustomLoadMoreView
import kotlin.properties.Delegates


abstract class BaseApplication : Application(){


    companion object {
        var isDebug = BuildConfig.DEBUG
        var instance: BaseApplication by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        registerActivityLifecycleCallbacks(SwitchBackgroundCallbacks())
        // 在 Application 中配置全局自定义的 LoadMoreView
        LoadMoreModuleConfig.defLoadMoreView = CustomLoadMoreView()
    }

    //activity生命的回调
    private class SwitchBackgroundCallbacks : ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
            /**
             * 监听到 Activity创建事件 将该 Activity 加入list
             */
            AppManageHelper.pushActivity(activity)
        }

        override fun onActivityStarted(activity: Activity) {}
        override fun onActivityResumed(activity: Activity) {}
        override fun onActivityPaused(activity: Activity) {}
        override fun onActivityStopped(activity: Activity) {}
        override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle) {}
        override fun onActivityDestroyed(activity: Activity) {
            if (null == AppManageHelper.mActivityList || AppManageHelper.mActivityList.isEmpty()) {
                return
            }
            if (AppManageHelper.mActivityList.contains(activity)) {
                /**
                 * 监听到 Activity销毁事件 将该Activity 从list中移除
                 */
                AppManageHelper.popActivity(activity)
            }
        }
    }

}