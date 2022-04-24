package com.jj.sensorcollector.playground1.data

import android.util.Log
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
            Log.d("ABABC", "Starting new collector job")
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
        when (sensorData) {
            is SensorData.AccSample -> analyseSample(sensorData)
            is SensorData.Error -> handleSensorError(sensorData)
            else -> handleWrongSample(sensorData)
        }
    }

    private suspend fun analyseSample(sensorData: SensorData.AccSample) {
        val analysedSample = accThresholdAnalyzer.analyze(sensorData)
        sensorsRepository.insertAnalysedAccelerometerSample(analysedSample)
    }

    private fun handleSensorError(sensorData: SensorData.Error) {
        val analysisFailure = AnalysedSample.Error(
            sensorData,
            sensorData.errorType.errorCause,
            timeProvider.getNowMillis()
        )
        handleAnalysisError(analysisFailure)

        if (sensorData.errorType is SensorData.ErrorType.InitializationFailure) {
            collectorJob?.cancel()
        }
    }

    private fun handleWrongSample(sensorData: SensorData) {
        val analysisError = AnalysedSample.Error(sensorData, "WrongSample", timeProvider.getNowMillis())
        handleAnalysisError(analysisError)
    }

    private fun handleAnalysisError(analysisError: AnalysedSample.Error) {
        // TODO Save and Handle analysis errors!!!
    }
}