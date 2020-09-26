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
    init {
        View.inflate(context, R.layout.layout_empty, this)
    }


}