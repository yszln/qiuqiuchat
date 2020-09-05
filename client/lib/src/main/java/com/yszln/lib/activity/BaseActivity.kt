package com.yszln.lib.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * @author: yszln
 * @date: 2020/8/7 21:12
 * @description: activity 父类，负责加载布局
 * @history:
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId())
        initView()
    }


    /**
     * 布局id
     */
    abstract fun layoutId(): Int

    /**
     * 初始化view
     */
    abstract fun initView()

}