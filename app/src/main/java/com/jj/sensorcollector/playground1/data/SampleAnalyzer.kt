package com.jj.sensorcollector.playground1.data

import android.util.Log
import com.jj.sensorcollector.playground1.domain.samples.AccSampleAnalyzer
import com.jj.sensorcollector.playground1.domain.repository.AccelerometerRepository
import com.jj.sensorcollector.playground1.domain.SensorData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SampleAnalyzer(
    private val accelerometerRepository: AccelerometerRepository,
    private val analyzer1: AccSampleAnalyzer,
    private val analyzer2: AccSampleAnalyzer,
    private val analyzer3: AccSampleAnalyzer
) {

    private var collectorJob: Job? = null

    fun startAnalysis() {
        collectorJob = CoroutineScope(Dispatchers.IO).launch {
            accelerometerRepository.collectAccelerometerSamples().collect {
                onSampleAvailable(it)
            }
        }
    }

    fun stopAnalysis() {
        collectorJob?.cancel()
        collectorJob = null
    }

    private fun onSampleAvailable(sensorData: SensorData) {
        if (sensorData is SensorData.AccSample) {
            val result1 = analyzer1.analyze(sensorData)
            val result2 = analyzer2.analyze(sensorData)
            val result3 = analyzer3.analyze(sensorData)

            val finalResult = (result1.analysedX.value ?: 1f) *
                (result2.analysedY.value ?: 1f) *
                (result3.analysedZ.value ?: 1f)

            Log.d("ABAB", "Final analysis: $finalResult")
        }
    }
}