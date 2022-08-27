package com.jj.core.data.hardware.gps.analysis

import android.util.Log
import com.jj.domain.coroutines.CoroutineScopeProvider
import com.jj.domain.hardware.gps.analysis.GPSPathAnalyser
import com.jj.domain.hardware.gps.analysis.GPSVelocityCalculator
import com.jj.domain.hardware.gps.repository.GPSRepository
import com.jj.domain.hardware.gps.repository.PathRepository
import com.jj.domain.model.analysis.AnalysedSample
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

    override fun start() {
        /* no-op */
    }

    private fun onAnalysedSampleAvailable(analysedGPSSample: AnalysedSample.AnalysedGPSSample) {
        previousSample?.let { prevSample ->
            val velocity = gpsVelocityCalculator.calculateCurrentVelocity(prevSample, analysedGPSSample)

            val roundedVelocity = ((if (velocity.isNaN()) 0.0
            else velocity
                * 100).roundToInt()) / 100
            Log.d("ABABV", "Current velocity: $roundedVelocity")
        } ?: run {
            Log.d("ABABV", "Waiting for second sample")
        }
        previousSample = analysedGPSSample
    }
}