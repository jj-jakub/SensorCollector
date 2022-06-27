package com.jj.core.data.repository

import com.jj.core.domain.samples.SensorData
import com.jj.core.domain.repository.AccelerometerRepository
import com.jj.core.domain.repository.GyroscopeRepository
import com.jj.core.domain.repository.MagneticFieldRepository
import com.jj.core.domain.repository.SensorsRepository
import com.jj.core.domain.samples.analysis.AnalysedSample
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