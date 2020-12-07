package com.yszln.lib.activity

import com.yszln.lib.fragment.BaseFragment

abstract  class RootActivity: SuperActivity() {
    lateinit var currentFragment: BaseFragment<*>

}