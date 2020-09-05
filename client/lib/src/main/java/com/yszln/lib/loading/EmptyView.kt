package com.yszln.lib.loading

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.yszln.lib.R
import com.yszln.lib.activity.BaseVMActivity
import com.yszln.lib.fragment.BaseVMFragment
import kotlinx.android.synthetic.main.layout_empty.view.*

/**
 * 空布局
 */
class EmptyView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    init {
        View.inflate(context, R.layout.layout_empty,this)
        empty_refresh.setOnClickListener {
            refresh()
        }
    }

    private fun refresh() {
        when(context){
            is BaseVMFragment<*>->{
                (context as BaseVMFragment<*> ).onRefresh()
            }
            is BaseVMActivity<*>->{
                (context as BaseVMActivity<*> ).onRefresh()
            }
        }
    }
}