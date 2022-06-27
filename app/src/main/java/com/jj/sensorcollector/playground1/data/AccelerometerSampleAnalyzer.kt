package com.jj.sensorcollector.playground1.data

import android.util.Log
import com.jj.sensorcollector.framework.utils.shouldStartNewJob
import com.jj.core.domain.coroutines.CoroutineScopeProvider
import com.jj.sensorcollector.playground1.domain.repository.SensorsRepository
import com.jj.sensors.domain.samples.SensorData
import com.jj.sensors.domain.samples.accelerometer.AccThresholdAnalyzer
import com.jj.sensors.domain.samples.analysis.AnalysedSample
import com.jj.sensorcollector.playground1.domain.time.TimeProvider
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AccelerometerSampleAnalyzer(
    private val sensorsRepository: SensorsRepository,
    private val accThresholdAnalyzer: AccThresholdAnalyzer,
    private val timeProvider: TimeProvider,
    private val coroutineScopeProvider: CoroutineScopeProvider
) {

    private var collectorJob: Job? = null

    fun startAnalysis() {
        if (collectorJob.shouldStartNewJob()) {
            Log.d("ABABC", "Starting new collector job")
            collectorJob = coroutineScopeProvider.getIOScope().launch {
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
            Log.d("ABABC", "Init error")
            collectorJob?.cancel()
        }
    }

    private fun handleWrongSample(sensorData: SensorData) {
        val analysisError = AnalysedSample.Error(sensorData, "WrongSample", timeProvider.getNowMillis())
        handleAnalysisError(analysisError)
    }

    private fun handleAnalysisError(analysisError: AnalysedSample.Error) {
        Log.d("ABABC", "analysisError: ${analysisError.errorCause}")
        // TODO Save and Handle analysis errors!!!
    }
}