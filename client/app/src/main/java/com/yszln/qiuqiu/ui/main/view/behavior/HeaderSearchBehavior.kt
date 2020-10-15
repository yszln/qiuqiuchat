package com.yszln.qiuqiu.ui.main.view.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.yszln.qiuqiu.R

class HeaderSearchBehavior(context: Context?, attrs: AttributeSet?) :
    CoordinatorLayout.Behavior<View>(context, attrs) {
    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        return dependency != null && dependency.id == R.id.image
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        if (dependency != null && dependency.id == R.id.image) {
            val resources = dependency.resources
            val progress = 1f -
                    Math.abs(dependency.translationY / (dependency.height - resources.getDimension(R.dimen.collapsed_header_height)))
            val header_height = resources.getDimension(R.dimen.collapsed_header_height)
            val offset_y = resources.getDimension(R.dimen.collapsed_float_offset_y)
            val child_height = child.height.toFloat()
            val child_height_end = (header_height - child_height) / 2 - (header_height + offset_y)
            child.translationY =
                header_height + resources.getDimension(R.dimen.collapsed_float_offset_y)


            // Margins
            val collapsedMargin = resources.getDimension(R.dimen.collapsed_search_margin_right)
            val zero = resources.getDimension(R.dimen.collapsed_float_zero)
            val marginLeftRight = resources.getDimension(R.dimen.collapsed_search_margin_left2right)
            val marginRight =
                (collapsedMargin + (marginLeftRight - collapsedMargin) * progress).toInt()
            val marginTop = (child_height_end + (zero - child_height_end) * progress).toInt()
            val marginLeft = marginLeftRight.toInt()
            val lp = child.layoutParams as CoordinatorLayout.LayoutParams
            lp.setMargins(marginLeft, marginTop, marginRight, 0)
            child.layoutParams = lp
            return true
        }
        return false
    }
}