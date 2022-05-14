package com.jj.sensorcollector.playground1.data.samples.gps

import com.jj.sensorcollector.playground1.domain.repository.GPSRepository
import com.jj.sensorcollector.playground1.domain.repository.PathRepository
import com.jj.sensorcollector.playground1.domain.samples.analysis.AnalysedSample
import com.jj.sensorcollector.playground1.domain.samples.gps.GPSPathAnalyser
import com.jj.sensorcollector.playground1.domain.samples.gps.PathData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DefaultGPSPathAnalyser(
    private val gpsRepository: GPSRepository,
    private val pathRepository: PathRepository,
    scope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) : GPSPathAnalyser {

    init {
        scope.launch {
            gpsRepository.collectAnalysedGPSSamples().collect {
                onAnalysedSampleAvailable(it)
            }
        }
    }

    private fun onAnalysedSampleAvailable(analysedGPSSample: AnalysedSample.AnalysedGPSSample) {
        pathRepository.insertData(PathData(analysedGPSSample))
    }
}