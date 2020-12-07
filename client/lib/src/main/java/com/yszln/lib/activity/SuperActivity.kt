package com.yszln.lib.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.yszln.lib.ibase.IBaseView

/**
 * @author: yszln
 * @date: 2020/8/7 21:12
 * @description: activity 父类，负责加载布局
 * @history:
 */
abstract class SuperActivity : AppCompatActivity() , IBaseView,View.OnClickListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId())
        for (view in registerClick()) {
            view.setOnClickListener(this)
        }
        initIntentData()
        initView(savedInstanceState)

    }

}