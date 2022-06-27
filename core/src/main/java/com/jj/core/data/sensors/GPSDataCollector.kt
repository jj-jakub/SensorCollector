package com.jj.core.data.sensors

import com.jj.core.domain.sensors.IDataCollector
import com.jj.core.domain.sensors.SensorData
import com.jj.core.framework.utils.BufferedMutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class GPSDataCollector : IDataCollector<SensorData.GPSData> {

    private val _dataFlow = BufferedMutableSharedFlow<SensorData.GPSData>()
    override val dataFlow: SharedFlow<SensorData.GPSData> = _dataFlow.asSharedFlow()

    override fun onDataReceived(data: SensorData.GPSData) {
        _dataFlow.tryEmit(data)
    }
}