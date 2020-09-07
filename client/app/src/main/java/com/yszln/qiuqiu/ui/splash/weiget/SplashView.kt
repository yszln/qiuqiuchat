package com.yszln.qiuqiu.ui.splash.weiget

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.PointF
import android.util.AttributeSet
import android.view.ViewGroup
import android.view.animation.*
import android.widget.FrameLayout
import android.widget.ImageView
import com.yszln.lib.utils.ScreenUtil
import com.yszln.qiuqiu.R
import java.util.*

/**
 * 启动页动画
 */
class SplashView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var mWidth = 0f
    private var mHeight = 0f
    private val logoCount = 66
    private var mItemWidth = 0f
    private var mAnimatorDuration = 2000L
    private var mLogoSize = ScreenUtil.dpToPx(30f)

    private var mInterpolatorList = ArrayList<Interpolator>()

    /**
     * 随即数
     */
    private var mRandom = Random();

    init {
        mInterpolatorList.add(LinearInterpolator())//线性
        mInterpolatorList.add(AccelerateDecelerateInterpolator())//先加速后减速
        mInterpolatorList.add(AccelerateInterpolator())//加速
        mInterpolatorList.add(DecelerateInterpolator())//减速
        post {
            addLogo()
        }

    }

    private fun addLogo() {
        for (position in 0..logoCount) {

            val imageView = ImageView(context)
            imageView.setImageResource(R.drawable.ic_logo)
            imageView.layoutParams =
                ViewGroup.LayoutParams(mLogoSize, mLogoSize)
            setAnimator(imageView, position)

            addView(imageView)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mWidth = measuredWidth.toFloat()
        mHeight = measuredHeight.toFloat()
        mItemWidth = mWidth / logoCount
    }

    private fun setAnimator(imageView: ImageView, position: Int) {

        val animators = AnimatorSet()

        val x = mItemWidth * position
        val startPoint = PointF(x, (-mLogoSize).toFloat())
        val endPoint = PointF(x, mLogoSize + mHeight)
        val bezierEvaluator = BezierEvaluator(getRandomPoint(), getRandomPoint())
        val valueAnimator = ObjectAnimator.ofObject(bezierEvaluator, startPoint, endPoint)
        valueAnimator.setEvaluator(bezierEvaluator)

        val nextInt = mRandom.nextInt(mInterpolatorList.size)
        valueAnimator.interpolator = mInterpolatorList[nextInt]
        valueAnimator.addUpdateListener {
            val point = it.animatedValue as PointF
            imageView.x = point.x
            imageView.y = point.y
        }


        val alphaAnimator = ObjectAnimator.ofFloat(imageView, "alpha", 1f, 0.1f)

        alphaAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                removeView(imageView)
            }
        })
        animators.duration = mAnimatorDuration
        animators.playTogether(valueAnimator, alphaAnimator)
        animators.start()


    }

    private fun getRandomPoint(): PointF {
        return PointF(getRandomWidth(), getRandomHeight())
    }

    /**
     * 生成区间内随机横坐标
     */
    private fun getRandomWidth(): Float {
        //占满控件
        return (mRandom.nextInt((mWidth).toInt())).toFloat()
    }

    /**
     * 生成区间内随机纵坐标
     */
    private fun getRandomHeight(): Float {
        val max = mHeight / 3 * 2 - mHeight / 2
        val min = mHeight / 3 - mHeight / 2
        return (mRandom.nextInt((max - min).toInt()) + min).toFloat()
    }

    override fun onDetachedFromWindow() {
        removeAllViews()
        super.onDetachedFromWindow()
    }


}