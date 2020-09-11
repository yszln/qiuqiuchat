package com.yszln.lib.bus

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData

object LiveDataBus {
    private val mHandler = Handler(Looper.getMainLooper())
    private val bus: MutableMap<String, MutableLiveData<Any>> = HashMap()

    fun getChannel(target: String): MutableLiveData<Any> {

        if (bus[target] == null) {
            val mutableLiveData = MutableLiveData<Any>()
            bus[target] = mutableLiveData
            return mutableLiveData
        }
        return bus[target] ?: MutableLiveData();
    }

    fun post(target: String, any: Any) {
        mHandler.post { getChannel(target).value = any }
    }


}