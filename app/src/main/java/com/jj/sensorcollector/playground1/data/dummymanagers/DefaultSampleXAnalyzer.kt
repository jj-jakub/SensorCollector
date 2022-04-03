package com.jj.sensorcollector.playground1.data.dummymanagers

import android.util.Log
import com.jj.sensorcollector.playground1.domain.repository.AccelerometerRepository
import com.jj.sensorcollector.playground1.domain.SensorData
import com.jj.sensorcollector.playground1.domain.managers.SampleXAnalyzer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DefaultSampleXAnalyzer(
    private val repository: AccelerometerRepository
) : SampleXAnalyzer {

    init {
        Log.d("ABAB", "DefaultSampleXAnalyzer created")

        CoroutineScope(Dispatchers.IO).launch {
            repository.collectAccelerometerSamples().collect {
                Log.d("ABAB", "Collecting... X is ${(it as SensorData.AccSample).x}")
                repository.sendSample()
                delay(2000L)
            }
        }
    }
}