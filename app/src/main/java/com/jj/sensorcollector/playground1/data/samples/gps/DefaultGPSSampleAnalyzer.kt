package com.jj.sensorcollector.playground1.data.samples.gps

import com.jj.sensorcollector.playground1.domain.repository.GPSRepository
import com.jj.sensorcollector.playground1.domain.samples.SensorData
import com.jj.sensorcollector.playground1.domain.samples.analysis.AnalysedSample
import com.jj.sensorcollector.playground1.domain.samples.gps.GPSSampleAnalyzer
import com.jj.sensorcollector.playground1.domain.time.TimeProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DefaultGPSSampleAnalyzer(
    private val timeProvider: TimeProvider,
    private val gpsRepository: GPSRepository
) : GPSSampleAnalyzer {

    private var collectorJob: Job? = null

    override fun startAnalysis() {
        collectorJob = CoroutineScope(Dispatchers.IO).launch {
            // Consider it to have independent collector that runs forever
            gpsRepository.collectGPSSamples().collect {
                if (it is SensorData.GPSSample) {
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
        val analysedSample = when (sensorData) {
            is SensorData.GPSSample -> analyze(sensorData)
            is SensorData.Error -> AnalysedSample.Error(sensorData, sensorData.msg, timeProvider.getNowMillis())
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