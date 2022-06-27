package com.jj.sensorcollector.playground1.data.samples.gps

import android.util.Log
import com.jj.sensorcollector.playground1.domain.coroutines.CoroutineScopeProvider
import com.jj.sensorcollector.playground1.domain.repository.GPSRepository
import com.jj.sensorcollector.playground1.domain.repository.PathRepository
import com.jj.sensorcollector.playground1.domain.samples.analysis.AnalysedSample
import com.jj.sensorcollector.playground1.domain.samples.gps.GPSPathAnalyser
import com.jj.sensorcollector.playground1.domain.samples.gps.GPSVelocityCalculator
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class DefaultGPSPathAnalyser(
    private val gpsRepository: GPSRepository,
    private val pathRepository: PathRepository,
    private val gpsVelocityCalculator: GPSVelocityCalculator,
    coroutineScopeProvider: CoroutineScopeProvider
) : GPSPathAnalyser {

    private var previousSample: AnalysedSample.AnalysedGPSSample? = null

    init {
        coroutineScopeProvider.getIOScope().launch {
            gpsRepository.collectAnalysedGPSSamples().collect {
                onAnalysedSampleAvailable(it)
            }
        }
    }

    private fun onAnalysedSampleAvailable(analysedGPSSample: AnalysedSample.AnalysedGPSSample) {
        previousSample?.let { prevSample ->
            val velocity = gpsVelocityCalculator.calculateAverageVelocity(prevSample, analysedGPSSample)
            val roundedVelocity = ((velocity * 100).roundToInt()) / 100
            Log.d("ABABV", "Current velocity: $roundedVelocity")
        } ?: run {
            Log.d("ABABV", "Waiting for second sample")
        }
        previousSample = analysedGPSSample
    }
}