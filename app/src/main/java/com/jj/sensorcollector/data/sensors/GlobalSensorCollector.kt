package com.jj.sensorcollector.data.sensors

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class GlobalSensorCollector(
        private val accelerometerDataCollector: AccelerometerDataCollector,
        private val gpsDataCollector: GPSDataCollector
) {

    init {
        CoroutineScope(Dispatchers.Default).launch {
            accelerometerDataCollector.dataFlow.collect {
                Log.d("ABAB", "GlobalSensorCollector, collect acc: $it")
            }
        }

        CoroutineScope(Dispatchers.Default).launch {
            gpsDataCollector.dataFlow.collect {
                Log.d("ABAB", "GlobalSensorCollector, collect gps: $it")
            }
        }
    }
}