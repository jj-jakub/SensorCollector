package com.jj.sensorcollector.playground1.data

import com.jj.sensorcollector.framework.utils.shouldStartNewJob
import com.jj.sensorcollector.playground1.domain.repository.SensorsRepository
import com.jj.sensorcollector.playground1.domain.samples.SensorData
import com.jj.sensorcollector.playground1.domain.samples.accelerometer.AccThresholdAnalyzer
import com.jj.sensorcollector.playground1.domain.samples.analysis.AnalysedSample
import com.jj.sensorcollector.playground1.domain.time.TimeProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AccelerometerSampleAnalyzer(
    private val sensorsRepository: SensorsRepository,
    private val accThresholdAnalyzer: AccThresholdAnalyzer,
    private val timeProvider: TimeProvider
) {

    private var collectorJob: Job? = null

    fun startAnalysis() {
        if (collectorJob.shouldStartNewJob()) {
            collectorJob = CoroutineScope(Dispatchers.IO).launch {
                // Consider it to have independent collector that runs forever
                sensorsRepository.collectRawAccelerometerSamples().collect {
                    onSampleAvailable(it)
                }
            }
        }
    }

    fun stopAnalysis() {
        collectorJob?.cancel()
        collectorJob = null
    }

    private suspend fun onSampleAvailable(sensorData: SensorData) {
        val analysedSample = when (sensorData) {
            is SensorData.AccSample -> accThresholdAnalyzer.analyze(sensorData)
            is SensorData.Error -> AnalysedSample.Error(sensorData, sensorData.msg, timeProvider.getNowMillis())
            else -> AnalysedSample.Error(sensorData, "WrongSample", timeProvider.getNowMillis())
        }

        if (analysedSample is AnalysedSample.AnalysedAccSample)
            sensorsRepository.insertAnalysedAccelerometerSample(analysedSample)
        else {
            // TODO Handle analysis errors!!!
        }
    }
}