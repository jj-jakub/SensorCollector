package com.jj.core.data.hardware.gps.analysis

import com.jj.domain.coroutines.CoroutineScopeProvider
import com.jj.domain.hardware.gps.repository.GPSRepository
import com.jj.domain.hardware.model.SensorData
import com.jj.domain.model.analysis.AnalysedSample
import com.jj.domain.hardware.gps.analysis.GPSSampleAnalyser
import com.jj.domain.time.TimeProvider
import com.jj.domain.utils.shouldStartNewJob
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DefaultGPSSampleAnalyser(
    private val timeProvider: TimeProvider,
    private val gpsRepository: GPSRepository,
    private val coroutineScopeProvider: CoroutineScopeProvider
) : GPSSampleAnalyser {

    private var collectorJob: Job? = null

    override fun startAnalysis() {
        if (collectorJob.shouldStartNewJob()) {
            collectorJob = coroutineScopeProvider.getIOScope().launch {
                // Consider it to have independent collector that runs forever
                gpsRepository.collectGPSSamples().collect {
                    if (it is SensorData.GPSSample) {
                        onSampleAvailable(it)
                    }
                }
            }
        }
    }

    override fun stopAnalysis() {
        collectorJob?.cancel()
        collectorJob = null
    }

    private suspend fun onSampleAvailable(sensorData: SensorData) {
        val analysedSample = when (sensorData) {
            is SensorData.GPSSample -> performAnalysis(sensorData)
            is SensorData.Error -> AnalysedSample.Error(sensorData, sensorData.errorType.errorCause, timeProvider.getNowMillis())
            else -> AnalysedSample.Error(sensorData, "WrongSample", timeProvider.getNowMillis())
        }

        if (analysedSample is AnalysedSample.AnalysedGPSSample)
            gpsRepository.insertAnalysedGPSSample(analysedSample)
        else {
            // TODO Handle analysis errors!!!
            stopAnalysis()
        }
    }

    private fun performAnalysis(sensorData: SensorData.GPSSample): AnalysedSample.AnalysedGPSSample =
        AnalysedSample.AnalysedGPSSample(
            latitude = sensorData.latitude,
            longitude = sensorData.longitude,
            sampleTime = timeProvider.getNowMillis()
        )
}