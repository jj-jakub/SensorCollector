package com.jj.core.data.coroutines

import com.jj.domain.coroutines.CoroutineScopeProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class DefaultCoroutineScopeProvider: CoroutineScopeProvider {
    override val io = Dispatchers.IO
    override val main = Dispatchers.Main
    override val default = Dispatchers.Default

    override fun getIOScope() = CoroutineScope(io)
    override fun getMainScope() = CoroutineScope(main)
    override fun getDefaultScope() = CoroutineScope(default)
}