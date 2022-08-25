package com.jj.domain.hardware.general

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface ISensorManager<T> {
    fun collectRawSensorSamples(): Flow<T>
    fun collectIsActiveState(): StateFlow<Boolean>
}