package com.yszln.qiuqiu.ui.main.view.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.yszln.qiuqiu.R
import kotlin.math.abs

class HeaderLogoBehavior(context: Context, attrs: AttributeSet) :
    CoordinatorLayout.Behavior<View>(context, attrs) {

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        return if (dependency != null && dependency.id == R.id.image) {
            true
        } else super.layoutDependsOn(parent, child, dependency)
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        return if (dependency != null && dependency.id == R.id.image) {
            val resources = dependency.resources
            val progress = 1f -
                    abs(dependency.translationY / (dependency.height - resources.getDimension(R.dimen.collapsed_header_height)))
            val tv = child.findViewById<TextView>(R.id.toolbar_tv)
            tv.alpha = progress
            true
        } else false
    }
}