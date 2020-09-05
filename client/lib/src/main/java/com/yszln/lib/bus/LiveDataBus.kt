package com.yszln.lib.bus

import androidx.lifecycle.MutableLiveData

object LiveDataBus {

    private val bus: MutableMap<String, MutableLiveData<Any>> = HashMap()

    fun getChannel(target: String): MutableLiveData<Any> {

        if (bus[target] == null) {
            val mutableLiveData = MutableLiveData<Any>()
            bus[target] = mutableLiveData
            return mutableLiveData
        }
        return bus[target] ?: MutableLiveData();
    }
}