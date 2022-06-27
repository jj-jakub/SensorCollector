package com.jj.core.data.sensors

import android.util.Log
import com.jj.core.domain.coroutines.CoroutineScopeProvider
import com.jj.core.domain.sensors.SamplesRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class GlobalSensorCollector(
    private val accelerometerDataCollector: AccelerometerDataCollector,
    private val gpsDataCollector: GPSDataCollector,
    private val repository: SamplesRepository,
    coroutineScopeProvider: CoroutineScopeProvider
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