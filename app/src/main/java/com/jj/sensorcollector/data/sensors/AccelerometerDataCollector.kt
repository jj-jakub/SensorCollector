package com.jj.sensorcollector.data.sensors

import com.jj.sensorcollector.domain.sensors.IDataCollector
import com.jj.sensorcollector.domain.sensors.SensorData.AccelerometerData
import com.jj.sensorcollector.framework.utils.BufferedMutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class AccelerometerDataCollector : IDataCollector<AccelerometerData> {

    private val _dataFlow = BufferedMutableSharedFlow<AccelerometerData>()
    override val dataFlow: SharedFlow<AccelerometerData> = _dataFlow.asSharedFlow()

    override fun onDataReceived(data: AccelerometerData) {
        _dataFlow.tryEmit(data)
    }
}