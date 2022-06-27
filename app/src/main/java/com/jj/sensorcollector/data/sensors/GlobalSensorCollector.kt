package com.jj.sensorcollector.data.sensors

import android.util.Log
import com.jj.sensorcollector.domain.sensors.SamplesRepository
import com.jj.core.coroutines.CoroutineScopeProvider
import kotlinx.coroutines.launch

class GlobalSensorCollector(
    private val accelerometerDataCollector: AccelerometerDataCollector,
    private val gpsDataCollector: GPSDataCollector,
    private val repository: SamplesRepository,
    coroutineScopeProvider: com.jj.core.coroutines.CoroutineScopeProvider
) {

    init {
        coroutineScopeProvider.getDefaultScope().launch {
            accelerometerDataCollector.dataFlow.collect {
                Log.d("ABAB", "GlobalSensorCollector, collect acc: $it")
                repository.insert(it)
            }
        }

        coroutineScopeProvider.getDefaultScope().launch {
            gpsDataCollector.dataFlow.collect {
                Log.d("ABAB", "GlobalSensorCollector, collect gps: $it")
                repository.insert(it)
            }
        }
    }

    fun ping() {
        /* no-op */
    }
}