package com.yszln.lib.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yszln.lib.network.ApiException
import com.yszln.lib.utils.toast
import kotlinx.coroutines.*

/**
 * 协程执行
 */
typealias Block<T> = suspend () -> T

/**
 * 错误
 */
typealias Error = suspend (e: Exception) -> Unit

/**
 * 取消
 */
typealias Cancel = suspend (e: Exception) -> Unit


open class BaseViewModel : ViewModel() {

    var mLoadingStatus=MutableLiveData<Boolean>()


    /**
     * 创建并执行协程
     * @param error 错误的处理
     * @param cancel 取消
     * @param showToast 错误是否显示吐司
     */
    fun launch(
        block: Block<Unit>,
        error: Error? = null,
        cancel: Cancel? = null,
        showToast: Boolean = true
    ): Job {
        mLoadingStatus.value=true
        return viewModelScope.launch {
            try {
                //执行block
                block.invoke()
                mLoadingStatus.value=false
            } catch (e: Exception) {
                mLoadingStatus.value=false
                if (e is CancellationException) {
                    //取消
                    cancel?.invoke(e)
                } else {
                    //处理异常
                    handlerException(e, showToast)
                    //抛出异常
                    error?.invoke(e)
                }

            }
        }
    }

    /**
     * 创建并执行协程
     * @param block 协程中执行
     * @return Deferred<T>
     */
    protected fun <T> async(block: Block<T>): Deferred<T> {
        return viewModelScope.async { block.invoke() }
    }

    /**
     * 取消协程任务
     */
    fun cancel(job: Job?) {
        job?.run {
            if (isActive && !isCompleted && !isCancelled)
                job.cancel()
        }
    }

    /**
     * 处理异常
     */
    private fun handlerException(e: Exception, showToast: Boolean) {
        when (e) {
            is ApiException -> {
                handlerApiException(e, showToast)
            }
            else -> {
                if (showToast) e.message?.toast()
            }
        }
    }

    /**
     * 处理后台返回的不同状态码
     */
    private fun handlerApiException(e: ApiException, showToast: Boolean) {
        if (showToast) e.message?.let { it.toast() }
        when (e.code) {

        }
    }
}

