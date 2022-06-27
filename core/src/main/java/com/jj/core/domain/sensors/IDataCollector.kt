package com.jj.core.domain.sensors

import kotlinx.coroutines.flow.SharedFlow

interface IDataCollector<T : ISensorData> {

    val dataFlow: SharedFlow<T>

    fun onDataReceived(data: T)
}