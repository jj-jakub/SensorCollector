package com.jj.sensorcollector.playground1.data

import android.util.Log
import com.jj.sensorcollector.playground1.domain.AccSampleAnalyzer
import com.jj.sensorcollector.playground1.domain.repository.AccelerometerRepository
import com.jj.sensorcollector.playground1.domain.SensorData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SampleAnalyzer(
    private val accelerometerRepository: AccelerometerRepository,
    private val analyzer1: AccSampleAnalyzer,
    private val analyzer2: AccSampleAnalyzer,
    private val analyzer3: AccSampleAnalyzer
) {

    fun startAnalysis() {
        CoroutineScope(Dispatchers.IO).launch {
            accelerometerRepository.collectAccelerometerSamples().collect {
                onSampleAvailable(it)
            }
        }
    }

    private fun onSampleAvailable(sensorData: SensorData) {
        if (sensorData is SensorData.AccSample) {
            val result1 = analyzer1.analyze(sensorData)
            val result2 = analyzer2.analyze(sensorData)
            val result3 = analyzer3.analyze(sensorData)

            val finalResult = result1 * result2 * result3

            Log.d("ABAB", "Final analysis: $finalResult")
        }
    }
}