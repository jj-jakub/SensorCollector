package com.jj.sensorcollector.playground1.framework.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.jj.sensorcollector.framework.utils.BufferedMutableSharedFlow
import com.jj.sensorcollector.playground1.domain.AccelerometerRepository
import com.jj.sensorcollector.playground1.framework.SensorData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AccelerometerDataViewModel(
    private val accelerometerRepository: AccelerometerRepository
): ViewModel() {

    private val _accelerometerSamples = BufferedMutableSharedFlow<SensorData>()
    val accelerometerSamples = _accelerometerSamples.asSharedFlow()

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private var collectingJob: Job? = null

    init {
        viewModelScope.launch {
            startCollectingJob()
            delay(1000L)
            collectingJob?.cancel()
            delay(1000L)
            startCollectingJob()
            delay(1000L)
            collectingJob?.cancel()
            delay(1000L)
            startCollectingJob()
            collectingJob?.cancel()
            collectingJob?.cancel()
        }
    }

    private fun startCollectingJob() {
        collectingJob = coroutineScope.launch {
            //        CoroutineScope(Dispatchers.IO).launch {
            accelerometerRepository.collectAccelerometerSamples().collect {
                _accelerometerSamples.tryEmit(it)
            }
        }
    }
}