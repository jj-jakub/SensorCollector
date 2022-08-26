package com.jj.domain.hardware.general

import com.jj.domain.hardware.general.model.SensorActivityState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface ISensorManager<T> {
    fun collectRawSensorSamples(): Flow<T>
    fun collectSensorActivityState(): StateFlow<SensorActivityState>
}