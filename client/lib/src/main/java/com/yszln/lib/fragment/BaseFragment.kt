package com.yszln.lib.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment


/**
 * @author: yszln
 * @date: 2020/8/9 21:52
 * @description:fragment 父类，负责加载布局
 * @history:
 */
abstract class BaseFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId(),container,false)
    }


    /**
     * 布局id
     */
    abstract fun layoutId(): Int



}