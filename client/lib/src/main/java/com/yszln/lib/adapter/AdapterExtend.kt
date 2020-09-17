package com.yszln.lib.adapter

import android.widget.ImageView
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yszln.lib.utils.load

/**
 * @author: yszln
 * @date: 2020/8/7 21:08
 * @description: adapter的扩展方法
 * @history:
 */

fun BaseViewHolder.load(viewId: Int, url: Any): BaseViewHolder {
    getView<ImageView>(viewId).load(url)
    return this
}

fun BaseViewHolder.loadCircle(viewId: Int, url: Any): BaseViewHolder {
    getView<ImageView>(viewId).load(url, 60)
    return this
}

fun BaseViewHolder.loadRound(viewId: Int, url: Any, roundedCorners: Int): BaseViewHolder {
    getView<ImageView>(viewId).load(url, roundedCorners)
    return this
}