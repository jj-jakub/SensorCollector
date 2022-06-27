package com.jj.core.data.samples.gps

import com.jj.core.domain.coroutines.CoroutineScopeProvider
import com.jj.core.domain.repository.GPSRepository
import com.jj.core.domain.samples.SensorData
import com.jj.core.domain.samples.analysis.AnalysedSample
import com.jj.core.domain.samples.samples.gps.GPSSampleAnalyzer
import com.jj.core.domain.time.TimeProvider
import com.jj.core.framework.utils.shouldStartNewJob
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DefaultGPSSampleAnalyzer(
    private val timeProvider: TimeProvider,
    private val gpsRepository: GPSRepository,
    private val coroutineScopeProvider: CoroutineScopeProvider
) : GPSSampleAnalyzer {

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
            is SensorData.GPSSample -> analyze(sensorData)
            is SensorData.Error -> AnalysedSample.Error(sensorData, sensorData.errorType.errorCause, timeProvider.getNowMillis())
            else -> AnalysedSample.Error(sensorData, "WrongSample", timeProvider.getNowMillis())
        }

        if (analysedSample is AnalysedSample.AnalysedGPSSample)
            gpsRepository.insertAnalysedGPSSample(analysedSample)
        else {
            // TODO Handle analysis errors!!!
        }
    }

    private fun analyze(sensorData: SensorData.GPSSample): AnalysedSample.AnalysedGPSSample {

        return AnalysedSample.AnalysedGPSSample(
            latitude = sensorData.latitude,
            longitude = sensorData.longitude,
            sampleTime = timeProvider.getNowMillis()
        )
    }
}