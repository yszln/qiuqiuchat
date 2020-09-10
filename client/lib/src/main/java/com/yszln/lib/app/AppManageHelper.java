package com.yszln.lib.app;

import android.app.Activity;
import android.util.Log;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Activity统一管理的辅助类
 */

public class AppManageHelper {

    private static final String TAG = "AppManageHelper";

    /**
     * 维护Activity 的list
     */
    public static List<Activity> mActivityList = Collections
            .synchronizedList(new LinkedList<Activity>());

    /**
     * @param activity 作用说明 ：添加一个activity到管理里
     */
    public static void pushActivity(Activity activity) {
        mActivityList.add(activity);
        Log.i(TAG, "activityList:size:" + mActivityList.size());
    }

    /**
     * @param activity 作用说明 ：删除一个activity在管理里
     */
    public static void popActivity(Activity activity) {
        mActivityList.remove(activity);
        Log.i(TAG, "activityList:size:" + mActivityList.size());
    }

    /**
     * get current Activity 获取当前Activity（栈中最后一个压入的）
     */
    public static Activity currentActivity() {
        if (mActivityList == null || mActivityList.isEmpty()) {
            return null;
        }
        Activity activity = mActivityList.get(mActivityList.size() - 1);
        return activity;
    }

    /**
     * 结束当前Activity（栈中最后一个压入的）
     */
    public static void finishCurrentActivity() {
        if (mActivityList == null || mActivityList.isEmpty()) {
            return;
        }
        Activity activity = mActivityList.get(mActivityList.size() - 1);
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public static void finishActivity(Activity activity) {
        if (mActivityList == null || mActivityList.isEmpty()) {
            return;
        }
        if (activity != null) {
            mActivityList.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public static void finishActivity(Class<?> cls) {
        if (mActivityList == null || mActivityList.isEmpty()) {
            return;
        }
        for (Activity activity : mActivityList) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 按照指定类名找到activity
     *
     * @param cls
     * @return
     */
    public static Activity findActivity(Class<?> cls) {
        Activity targetActivity = null;
        if (mActivityList != null) {
            for (Activity activity : mActivityList) {
                if (activity.getClass().equals(cls)) {
                    targetActivity = activity;
                    break;
                }
            }
        }
        return targetActivity;
    }

    /**
     * @return 作用说明 ：获取当前最顶部activity的实例
     */
    public Activity getTopActivity() {
        Activity mBaseActivity = null;
        synchronized (mActivityList) {
            final int size = mActivityList.size() - 1;
            if (size < 0) {
                return null;
            }
            mBaseActivity = mActivityList.get(size);
        }
        return mBaseActivity;

    }

    /**
     * @return 作用说明 ：获取当前最顶部的acitivity 名字
     */
    public String getTopActivityName() {
        Activity mBaseActivity = null;
        synchronized (mActivityList) {
            final int size = mActivityList.size() - 1;
            if (size < 0) {
                return null;
            }
            mBaseActivity = mActivityList.get(size);
        }
        return mBaseActivity.getClass().getName();
    }

    /**
     * 结束所有Activity
     */
    public static void finishAllActivity() {
        if (mActivityList == null) {
            return;
        }
        for (Activity activity : mActivityList) {
            activity.finish();
        }
        mActivityList.clear();
    }

    /**
     * 退出应用程序
     */
    public static void exitApp() {
        try {
            Log.i(TAG, "app exit");
            finishAllActivity();
        } catch (Exception e) {
        }
    }
}