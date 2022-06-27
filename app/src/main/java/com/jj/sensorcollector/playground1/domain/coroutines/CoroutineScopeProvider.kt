package com.jj.sensorcollector.playground1.domain.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope

interface CoroutineScopeProvider {
    val io: CoroutineDispatcher
    val main: CoroutineDispatcher
    val default: CoroutineDispatcher

    fun getIOScope(): CoroutineScope
    fun getMainScope(): CoroutineScope
    fun getDefaultScope(): CoroutineScope
}