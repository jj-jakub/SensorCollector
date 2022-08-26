package com.jj.core.data.hardware.accelerometer.analysis

import android.util.Log
import com.jj.domain.coroutines.CoroutineScopeProvider
import com.jj.domain.hardware.general.SensorsRepository
import com.jj.domain.hardware.model.SensorData
import com.jj.domain.hardware.accelerometer.analysis.AccelerometerThresholdAnalyser
import com.jj.domain.model.analysis.AnalysedSample
import com.jj.domain.time.TimeProvider
import com.jj.domain.hardware.accelerometer.analysis.AccelerometerSampleAnalyser
import com.jj.domain.utils.shouldStartNewJob
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DefaultAccelerometerSampleAnalyser(
    private val sensorsRepository: SensorsRepository,
    private val accelerometerThresholdAnalyser: AccelerometerThresholdAnalyser,
    private val timeProvider: TimeProvider,
    private val coroutineScopeProvider: CoroutineScopeProvider
) : AccelerometerSampleAnalyser {

    private var collectorJob: Job? = null

    override fun startAnalysis() {
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

    override fun stopAnalysis() {
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
        val analysedSample = accelerometerThresholdAnalyser.performAnalysis(sensorData)
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
            stopAnalysis()
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