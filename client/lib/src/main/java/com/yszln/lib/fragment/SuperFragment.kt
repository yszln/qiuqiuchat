package com.yszln.lib.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yszln.lib.ibase.IBaseView


/**
 * @author: yszln
 * @date: 2020/8/9 21:52
 * @description:fragment 父类，负责加载布局
 * @history:
 */
abstract class SuperFragment : Fragment() ,View.OnClickListener,IBaseView{

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId(),container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(savedInstanceState)
        initIntentData()
        for (view in registerClick()) {
            view.setOnClickListener(this)
        }
    }



}