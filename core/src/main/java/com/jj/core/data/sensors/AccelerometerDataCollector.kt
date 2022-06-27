package com.jj.core.data.sensors

import com.jj.core.domain.sensors.IDataCollector
import com.jj.core.domain.sensors.SensorData
import com.jj.core.framework.utils.BufferedMutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class AccelerometerDataCollector : IDataCollector<SensorData.AccelerometerData> {

    private val _dataFlow = BufferedMutableSharedFlow<SensorData.AccelerometerData>()
    override val dataFlow: SharedFlow<SensorData.AccelerometerData> = _dataFlow.asSharedFlow()

    override fun onDataReceived(data: SensorData.AccelerometerData) {
        _dataFlow.tryEmit(data)
    }
}