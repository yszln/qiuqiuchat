package com.yszln.lib.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.Rect
import android.view.View
import com.yszln.lib.BaseApplication

object ScreenUtil {
    /**
     * 屏幕宽度
     */
    fun getScreenWidth(): Int {
        return Resources.getSystem().displayMetrics.widthPixels
    }

    /**
     * 屏幕高度
     */
    fun getScreenHeight(): Int {
        return Resources.getSystem().displayMetrics.heightPixels
    }

    /**
     * 获取状态栏高度
     */
    fun getStatusBarHeight(): Int {
        val statusBarHeight: Int
        val resId = Resources.getSystem()
            .getIdentifier("status_bar_height", "dimen", "android")
        statusBarHeight = if (resId > 0) {
            Resources.getSystem().getDimensionPixelSize(resId)
        } else {
            dpToPx(24f)
        }
        return statusBarHeight
    }

    /**
     * 根据手机的分辨率dp 转成px(像素)
     */
    fun dpToPx(dpValue: Float): Int {
        val scale =
            Resources.getSystem().displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * 根据手机的分辨率px(像素) 转成dp
     */
    fun pxToDp(pxValue: Float): Int {
        val scale = BaseApplication.mContext.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }


    fun spToPx(sp: Float): Float {
        val scale: Float =
            BaseApplication.mContext.resources.displayMetrics.scaledDensity
        return sp * scale
    }

    /**
     * 解决布局被输入法覆盖
     *
     * @param root         最外层布局，需要调整的布局
     * @param scrollToView 被键盘遮挡的scrollToView，滚动root,使scrollToView在root可视区域的底部
     */
    fun controlKeyboardLayout(
        root: View,
        scrollToView: View
    ) {
        root.viewTreeObserver.addOnGlobalLayoutListener {
            val rect = Rect()
            //获取root在窗体的可视区域
            root.getWindowVisibleDisplayFrame(rect)
            //获取root在窗体的不可视区域高度(被其他View遮挡的区域高度)
            val rootInvisibleHeight = root.rootView.height - rect.bottom
            //若不可视区域高度大于100，则键盘显示
            if (rootInvisibleHeight > 100) {
                val location = IntArray(2)
                //获取scrollToView在窗体的坐标
                scrollToView.getLocationInWindow(location)
                //计算root滚动高度，使scrollToView在可见区域的底部
                val srollHeight =
                    location[1] + scrollToView.height - rect.bottom
                root.scrollTo(0, srollHeight)
            } else {
                //键盘隐藏
                root.scrollTo(0, 0)
            }
        }
    }
}