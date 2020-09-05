package com.yszln.lib.widget.title

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.yszln.lib.R
import com.yszln.lib.utils.StatusBarUtil
import kotlinx.android.synthetic.main.view_title.view.*

class TitleBarView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    var mBlackIv: View
    var mTitleTv: View
    var mMoreIv: View

    fun setTitle(title: CharSequence?) {
        titleTitle.text = title
    }

    init {
        View.inflate(context, R.layout.view_title, this)
        mBlackIv = titleBlack
        mTitleTv = titleTitle
        mMoreIv = titleMore
        titleBlack.setOnClickListener {
            if (context is Activity) {
                context.finish()
            }
        }

        if (context is AppCompatActivity) {
            //设置标题
            setTitle(context.title)
            //设置沉浸式状态栏
            StatusBarUtil.immersive(context)
            StatusBarUtil.darkMode(context)
            StatusBarUtil.setPaddingSmart(context, this)
        }
    }
}