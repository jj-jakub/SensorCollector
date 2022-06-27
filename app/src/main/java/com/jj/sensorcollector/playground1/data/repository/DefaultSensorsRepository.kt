package com.jj.sensorcollector.playground1.data.repository

import com.jj.sensors.domain.samples.SensorData
import com.jj.sensorcollector.playground1.domain.repository.AccelerometerRepository
import com.jj.sensorcollector.playground1.domain.repository.GyroscopeRepository
import com.jj.sensorcollector.playground1.domain.repository.MagneticFieldRepository
import com.jj.sensorcollector.playground1.domain.repository.SensorsRepository
import com.jj.sensors.domain.samples.analysis.AnalysedSample
import kotlinx.coroutines.flow.Flow

class DefaultSensorsRepository(
    private val accelerometerRepository: AccelerometerRepository,
    private val gyroscopeRepository: GyroscopeRepository,
    private val magneticFieldRepository: MagneticFieldRepository
) : SensorsRepository {

    override fun collectRawAccelerometerSamples(): Flow<SensorData> = accelerometerRepository.collectRawAccelerometerSamples()

    override fun collectAnalysedAccelerometerSamples(): Flow<AnalysedSample.AnalysedAccSample> =
        accelerometerRepository.collectAnalysedAccelerometerSamples()

    override suspend fun insertAnalysedAccelerometerSample(analysedAccSample: AnalysedSample.AnalysedAccSample) =
        accelerometerRepository.insertAnalysedAccelerometerSample(analysedAccSample)

    override suspend fun sendAccelerometerSample() = accelerometerRepository.sendSample()

    override fun collectGyroscopeSamples(): Flow<SensorData> = gyroscopeRepository.collectGyroscopeSamples()

    override fun collectMagneticFieldSamples(): Flow<SensorData> = magneticFieldRepository.collectMagneticFieldSamples()
}