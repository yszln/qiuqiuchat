package com.yszln.lib.bus

import com.jeremyliao.liveeventbus.LiveEventBus
import com.jeremyliao.liveeventbus.core.Observable

object LiveDataBus {

    fun getChannel(target: String): Observable<Any> {
        return  LiveEventBus.get(target)
    }

    fun post(target: String, any: Any) {
        LiveEventBus.get(target).post(any)
    }


}