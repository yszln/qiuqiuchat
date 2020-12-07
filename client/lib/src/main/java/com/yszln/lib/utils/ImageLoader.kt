package com.yszln.lib.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.yszln.lib.R


object ImageLoader {

    const val mCircleCorners = 360

    /**
     * 错误图片
     */
    private val mErrorImg = R.drawable.ic_default

    /**
     * 加载中的图片-占位图
     */
    private val mLoadingImg = R.drawable.ic_default

    /**
     * 加载图片
     * @param context
     * @param imageView 图片控件
     * @param url 图片路径
     * @param errorImg 加载失败的图片
     * @param loadingImg 加载中的占位
     * @param roundedCorners 圆角大小
     */
    fun load(
        context: Context,
        imageView: ImageView,
        url: Any,
        roundedCorners: Int = 0,
        errorImg: Int?,
        loadingImg: Int?,
        listener: RequestListener<Drawable>? = null
    ) {
        Glide.with(context)
            .load(url)
            .addListener(listener)
            .apply {

                if (roundedCorners >= mCircleCorners) {
                    //圆角
                    apply(RequestOptions.circleCropTransform())
                } else if (roundedCorners > 0) {
                    //圆形
                    apply(
                        RequestOptions.bitmapTransform(
                            RoundedCorners(
                                roundedCorners.px()
                            )
                        )
                    )
                }
                //错误图片
                error(errorImg ?: mErrorImg)
                //加载中图片
                placeholder(loadingImg ?: mLoadingImg)


            }.into(imageView)

    }

    /**
     * 加载图片
     */
    fun load(
        imageView: ImageView,
        url: Any,
        roundedCorners: Int = 0,
        errorImg: Int? = mErrorImg,
        loadingImg: Int? = mLoadingImg,
        listener: RequestListener<Drawable>? = null
    ) {
        load(imageView.context, imageView, url, roundedCorners, errorImg, loadingImg,listener)
    }

    /**
     * 加载圆形图片
     */
    fun loadCircle(
        context: Context,
        imageView: ImageView,
        url: Any,
        errorImg: Int = mErrorImg,
        loadingImg: Int = mLoadingImg
    ) {
        load(context, imageView, url, mCircleCorners, errorImg, loadingImg)
    }

    /**
     * 加载圆形图片
     */
    fun loadCircle(
        imageView: ImageView,
        url: Any,
        errorImg: Int? = mErrorImg,
        loadingImg: Int? = mLoadingImg
    ) {
        load(imageView.context, imageView, url, mCircleCorners, errorImg, loadingImg)
    }


}