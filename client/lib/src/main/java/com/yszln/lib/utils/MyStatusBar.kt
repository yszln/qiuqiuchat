package com.yszln.lib.utils

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity

class MyStatusBar {
    constructor(activity:AppCompatActivity){
        StatusBarUtil.immersive(activity)
        StatusBarUtil.darkMode(activity)
    }
}