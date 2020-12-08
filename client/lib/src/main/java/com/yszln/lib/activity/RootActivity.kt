package com.yszln.lib.activity

import android.view.View
import android.view.inputmethod.InputMethodManager
import com.yszln.lib.fragment.BaseFragment


abstract  class RootActivity: SuperActivity() {
    lateinit var currentFragment: BaseFragment<*>



    override fun onBackPressed() {
        //隐藏键盘
        hideKeyboard()
        super.onBackPressed()
    }


    open fun hideKeyboard() {
        val view: View? = currentFocus
        if (view != null) {
            val inputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                view.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }

}