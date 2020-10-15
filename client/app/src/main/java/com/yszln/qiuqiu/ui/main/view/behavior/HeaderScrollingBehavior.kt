package com.yszln.qiuqiu.ui.main.view.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.yszln.qiuqiu.R
import java.lang.ref.WeakReference


class HeaderScrollingBehavior(context: Context?, attrs: AttributeSet?) :
    CoordinatorLayout.Behavior<RecyclerView>(context, attrs) {
    private var dependentView: WeakReference<View>? = null
    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: RecyclerView,
        dependency: View
    ): Boolean {
        if (dependency != null && dependency.id == R.id.image) {
            if (dependentView == null) {
                dependentView = WeakReference(dependency)
            }
            return true
        }
        return false
    }

    override fun onLayoutChild(
        parent: CoordinatorLayout,
        child: RecyclerView,
        layoutDirection: Int
    ): Boolean {
        val lp = child.layoutParams as CoordinatorLayout.LayoutParams
        if (lp.height == CoordinatorLayout.LayoutParams.MATCH_PARENT) {
            child.layout(0, 0, parent.width, (parent.height - dependentViewCollapsedHeight).toInt())
            return true
        }
        return super.onLayoutChild(parent, child, layoutDirection)
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: RecyclerView,
        dependency: View
    ): Boolean {
        child.translationY = dependency.height + dependency.translationY
        return true
    }

    /**
     * 用户按下手指时触发，询问 NestedScrollParent 是否要处理这次滑动操作，
     * 如果返回 true 则表示“我要处理这次滑动”，如果返回 false 则表示“不处理”，
     * 后面的一系列回调函数就不会被调用了。它有一个关键的参数，就是滑动方向，
     * 表明了用户是垂直滑动还是水平滑动
     * @param coordinatorLayout
     * @param child
     * @param directTargetChild
     * @param target
     * @param axes
     * @param type
     * @return
     */
    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: RecyclerView,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean {
        return axes and ViewCompat.SCROLL_AXIS_VERTICAL != 0
    }

    /**
     * 当 NestedScrollParent 接受要处理本次滑动后，这个回调被调用，
     * 我们可以做一些准备工作，比如让之前的滑动动画结束。
     * @param coordinatorLayout
     * @param child
     * @param directTargetChild
     * @param target
     * @param axes
     * @param type
     */
    override fun onNestedScrollAccepted(
        coordinatorLayout: CoordinatorLayout,
        child: RecyclerView,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ) {
        super.onNestedScrollAccepted(
            coordinatorLayout,
            child,
            directTargetChild,
            target,
            axes,
            type
        )
    }

    /**
     * 当 NestedScrollChild 即将被滑动时调用，在这里你可以做一些处理。
     * 值得注意的是，这个方法有一个参数 int[] consumed，你可以修改这个数组来表示你到底处理掉了多少像素
     * 。假设用户滑动了 100px，你做了 90px 的位移，那么就需要把 consumed[1] 改成 90（下标 0、1 分别对应 x、y 轴）
     * ，这样 NestedScrollChild 就能知道，然后继续处理剩下的 10px。
     * @param coordinatorLayout
     * @param child
     * @param target
     * @param dx
     * @param dy
     * @param consumed
     * @param type
     */
    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout,
        child: RecyclerView,
        target: View,
        dx: Int,
        dy: Int,
        consumed: IntArray,
        type: Int
    ) {
        if (dy < 0) {
            return
        }
        val dependentView = getDependentView()
        val newTranslateY = dependentView!!.translationY - dy
        val minHeaderTranslate = -(dependentView.height - dependentViewCollapsedHeight)
        if (newTranslateY > minHeaderTranslate) {
            dependentView.translationY = newTranslateY
            consumed[1] = dy
        } else {
            dependentView.translationY = minHeaderTranslate
        }
    }

    /**
     * 上一个方法结束后，NestedScrollChild 处理剩下的距离。
     * 比如上面还剩 10px，这里 NSC 滚动 2px 后发现已经到头了，于是 NestedScrollChild 结束其滚动，调用该方法，
     * 并将 NestedScrollChild 处理剩下的像素数作为参数（dxUnconsumed、dyUnconsumed）传过来，这里传过来的就是 8px。
     * 参数中还会有 NestedScrollChild 处理过的像素数（dxConsumed、dyConsumed）。这个方法主要处理一些越界后的滚动。
     * @param coordinatorLayout
     * @param child
     * @param target
     * @param dxConsumed
     * @param dyConsumed
     * @param dxUnconsumed
     * @param dyUnconsumed
     * @param type
     */
    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: RecyclerView,
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int
    ) {
        if (dyUnconsumed > 0) {
            return
        }
        val dependentView = getDependentView()
        val newTranslateY = dependentView!!.translationY - dyUnconsumed
        val maxHeaderTranslate = 0f
        if (newTranslateY < maxHeaderTranslate) {
            dependentView.translationY = newTranslateY
        } else {
            dependentView.translationY = maxHeaderTranslate
        }
    }

    /**
     * 用户松开手指并且会发生惯性滚动之前调用。参数提供了速度信息，我们这里可以根据速度，
     * 决定最终的状态是展开还是折叠，并且启动滑动动画。通过返回值我们可以通知 NestedScrollChild 是否自己还要进行滑动滚动，
     * 一般情况如果面板处于中间态，我们就不让 NestedScrollChild 接着滚了，因为我们还要用动画把面板完全展开或者完全折叠。
     * 此处返回false，不拦截RecyclerView的惯性滚动
     * @param coordinatorLayout
     * @param child
     * @param target
     * @param velocityX
     * @param velocityY
     * @return
     */
    override fun onNestedPreFling(
        coordinatorLayout: CoordinatorLayout,
        child: RecyclerView,
        target: View,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        return false
    }

    /**
     * 一切滚动停止后调用，如果不会发生惯性滚动，fling 相关方法不会调用，直接执行到这里。
     * 这里我们做一些清理工作，当然有时也要处理中间态问题。
     * @param coordinatorLayout
     * @param child
     * @param target
     * @param type
     */
    override fun onStopNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: RecyclerView,
        target: View,
        type: Int
    ) {
    }

    private val dependentViewCollapsedHeight: Float
        private get() = getDependentView()!!.resources.getDimension(R.dimen.collapsed_header_height)

    private fun getDependentView(): View? {
        return dependentView!!.get()
    }
}