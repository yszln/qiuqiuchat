package com.yszln.lib.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.yszln.lib.R

object ImageLoader {
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
        errorImg: Int?,
        loadingImg: Int?,
        roundedCorners: Int?
    ) {
        Glide.with(context)
            .load(url)
//            .optionalCenterCrop()
            .centerCrop()
            .apply {
                roundedCorners?.let {
                    if (roundedCorners > 60) {
                        RequestOptions.bitmapTransform(
                            CircleCrop()
                        )
                    } else {
                        apply(
                            RequestOptions.bitmapTransform(
                                RoundedCorners(
                                    ScreenUtil.dpToPx(
                                        roundedCorners
                                    )
                                )
                            )
                        )
                    }
                }

                error(errorImg ?: R.drawable.ic_default)
                placeholder(loadingImg ?: R.drawable.ic_default)


            }.into(imageView);


    }

    fun load(
        context: Context,
        imageView: ImageView,
        url: Any,
        errorImg: Int?,
        loadingImg: Int?
    ) {
        load(imageView.context, imageView, url, errorImg, loadingImg, null)
    }

    fun load(
        imageView: ImageView,
        url: Any
    ) {
        load(imageView.context, imageView, url, null, null, null)
    }

    fun load(
        context: Context,
        imageView: ImageView,
        url: Any
    ) {
        load(context, imageView, url, null, null, null)
    }


    fun load(
        imageView: ImageView,
        url: Any,
        roundedCorners: Int?
    ) {
        load(imageView.context, imageView, url, null, null, roundedCorners)
    }
}