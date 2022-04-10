package com.jj.sensorcollector.playground1.domain.repository

import com.jj.sensorcollector.playground1.domain.samples.SensorData
import kotlinx.coroutines.flow.Flow

interface GPSRepository {

    fun collectGPSSamples(): Flow<SensorData>
}