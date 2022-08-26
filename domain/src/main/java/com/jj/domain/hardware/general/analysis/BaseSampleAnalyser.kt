package com.jj.domain.hardware.general.analysis

import com.jj.domain.coroutines.CoroutineScopeProvider
import com.jj.domain.hardware.model.SensorData
import com.jj.domain.model.analysis.AnalysedSample
import com.jj.domain.time.TimeProvider
import com.jj.domain.utils.shouldStartNewJob
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

abstract class BaseSampleAnalyser(
    private val timeProvider: TimeProvider,
    private val coroutineScopeProvider: CoroutineScopeProvider,
) {
    private var collectorJob: Job? = null

    abstract val samplesFlow: Flow<SensorData>

    fun startAnalysis() {
        if (collectorJob.shouldStartNewJob()) {
            collectorJob = coroutineScopeProvider.getIOScope().launch {
                // Consider it to have independent collector that runs forever
                samplesFlow.collect {
                    onSampleAvailable(it)
                }
            }
        }
    }

    protected abstract suspend fun onSampleAvailable(sensorData: SensorData)

    fun stopAnalysis() {
        collectorJob?.cancel()
        collectorJob = null
    }

    protected fun handleOtherSample(sensorData: SensorData) {
        if (sensorData is SensorData.Error) handleSensorError(sensorData)
        else handleWrongSample(sensorData)
    }

    private fun handleSensorError(sensorData: SensorData.Error) {
        val analysisFailure = AnalysedSample.Error(
            sensorData,
            sensorData.errorType.errorCause,
            timeProvider.getNowMillis()
        )
        handleAnalysisError(analysisFailure)

        if (sensorData.errorType is SensorData.ErrorType.InitializationFailure) {
            stopAnalysis()
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