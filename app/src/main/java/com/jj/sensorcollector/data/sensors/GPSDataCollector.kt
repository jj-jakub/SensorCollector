package com.jj.sensorcollector.data.sensors

import com.jj.sensorcollector.domain.sensors.IDataCollector
import com.jj.sensorcollector.domain.sensors.SensorData.AccelerometerData
import com.jj.sensorcollector.domain.sensors.SensorData.GPSData
import com.jj.sensorcollector.framework.utils.BufferedMutableSharedFlow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class GPSDataCollector : IDataCollector<GPSData> {

    private val _dataFlow = BufferedMutableSharedFlow<GPSData>()
    override val dataFlow: SharedFlow<GPSData> = _dataFlow.asSharedFlow()

    override fun onDataReceived(data: GPSData) {
        _dataFlow.tryEmit(data)
    }
}