package com.yszln.lib.viewmodel

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