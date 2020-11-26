package com.yszln.lib.loading

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.yszln.lib.R
import kotlinx.android.synthetic.main.layout_empty.view.*

/**
 * 空布局
 */
class EmptyView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {


    var mEmptyListener: EmptyListener? = null

    var mStatus = EmptyStatus.LOADING

    init {
        View.inflate(context, R.layout.layout_empty, this)

        mEmptyButton.setOnClickListener {
            mEmptyListener?.onRetry()
        }
    }

    fun setLoading() {
        setStatus(EmptyStatus.LOADING)
    }

    /**
     * 加载成功，判断数据是否为空
     */
    fun setSuccess(isEmpty: Boolean) {
        if (isEmpty) {
            setStatus(EmptyStatus.EMPTY)
        } else {
            setStatus(EmptyStatus.SUCCESS)
        }

    }

    /**
     * 加载失败，判断是否网络异常
     */
    fun setError(isEmpty: Boolean) {
        if (isEmpty) {
            if (NetworkUtil.isNetWorkConnected()) {
                setStatus(EmptyStatus.ERROR)
            } else {
                setStatus(EmptyStatus.NO_NET)
            }
        } else {
            setSuccess(isEmpty)
        }

    }

    fun isNetError(): Boolean {
        return mStatus == EmptyStatus.NO_NET
    }

    fun setStatus(status: EmptyStatus) {
        mStatus = status

        when (mStatus) {
            EmptyStatus.EMPTY -> {
                //空数据
                visibility = View.VISIBLE
                mEmptyButton.visibility = View.GONE
            }
            EmptyStatus.NO_NET -> {
                //网络异常
                visibility = View.VISIBLE
                mEmptyButton.visibility = View.VISIBLE
            }
            EmptyStatus.ERROR -> {
                //异常
                visibility = View.VISIBLE
                mEmptyButton.visibility = View.VISIBLE
            }
            EmptyStatus.LOADING -> {
                //加载
                visibility = View.VISIBLE
                mEmptyButton.visibility = View.GONE
            }
            EmptyStatus.SUCCESS -> {
                //加载成功
                visibility = View.GONE
                mEmptyButton.visibility = View.GONE
            }
        }
        mEmptyIcon.setImageResource(status.icon)
        mEmptyText.text = status.text
    }

    interface EmptyListener {
        /**
         * 重试
         */
        fun onRetry()
    }

}