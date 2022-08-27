package com.jj.core.data.hardware.gps.analysis

import com.jj.core.data.hardware.gps.analysis.model.Distances
import com.jj.core.data.hardware.gps.analysis.model.Velocities
import com.jj.domain.base.usecase.invoke
import com.jj.domain.coroutines.CoroutineScopeProvider
import com.jj.domain.hardware.gps.repository.GPSRepository
import com.jj.domain.hardware.gps.usecase.path.FinishPath
import com.jj.domain.hardware.gps.usecase.path.StartPath
import com.jj.domain.model.analysis.AnalysedSample
import com.jj.domain.utils.shouldStartNewJob
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PathCalculatorPersistence(
    private val gpsRepository: GPSRepository,
    private val startPath: StartPath,
    private val finishPath: FinishPath,
    private val coroutineScopeProvider: CoroutineScopeProvider,
    private val velocityCalculatorBufferPersistence: VelocityCalculatorBufferPersistence,
    private val distanceCalculatorBufferPersistence: DistanceCalculatorBufferPersistence,
) {

    private val _averagePathVelocity = MutableStateFlow(0.0)
    val averagePathVelocity = _averagePathVelocity.asStateFlow()
    private val _stackedPathDistance = MutableStateFlow(0.0)
    val stackedPathDistance = _stackedPathDistance.asStateFlow()
    private val _allSamplesPathDistance = MutableStateFlow(0.0)
    val allSamplesPathDistance = _allSamplesPathDistance.asStateFlow()

    private val _isPathRecording = MutableStateFlow(false)
    val isPathRecording = _isPathRecording.asStateFlow()

    private var pathId: Int? = null

    private var samplesCollectingJob: Job? = null

    suspend fun startTrackingPath() {
        withContext(coroutineScopeProvider.io) {
            pathId = startPath()
            if (samplesCollectingJob.shouldStartNewJob()) {
                startSamplesCollectingJob()
            }
        }
    }

    private fun startSamplesCollectingJob() {
        samplesCollectingJob = coroutineScopeProvider.getDefaultScope().launch {
            gpsRepository.collectAnalysedGPSSamples().collect {
                onSampleAvailable(it)
            }
        }
        _isPathRecording.value = true
    }

    private fun onSampleAvailable(analysedGPSSample: AnalysedSample.AnalysedGPSSample) {
        val velocities = velocityCalculatorBufferPersistence.onNewSample(analysedGPSSample)
        val distances = distanceCalculatorBufferPersistence.onNewSample(analysedGPSSample)
        process(velocities = velocities, distances = distances)
    }

    suspend fun stopTrackingPath() {
        withContext(coroutineScopeProvider.io) {
            pathId?.let { finishPath(it) }
            samplesCollectingJob?.cancel()
            samplesCollectingJob = null
            _isPathRecording.value = false
            val velocities = velocityCalculatorBufferPersistence.resetVelocities()
            val distances = distanceCalculatorBufferPersistence.resetDistances()

            process(velocities = velocities, distances = distances)
        }
    }

    private fun process(velocities: Velocities, distances: Distances) {
        val averagePathVelocity = velocities.stackedAverageVelocity
        val stackedPathDistance = distances.stackedDistanceKm
        val allSamplesPathDistance = distances.allSamplesDistanceKm

        _averagePathVelocity.value = averagePathVelocity
        _stackedPathDistance.value = stackedPathDistance
        _allSamplesPathDistance.value = allSamplesPathDistance
    }
}