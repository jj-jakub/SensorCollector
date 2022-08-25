package com.jj.core.data.hardware.general

import com.jj.domain.hardware.model.SensorData
import com.jj.domain.hardware.accelerometer.repository.AccelerometerRepository
import com.jj.domain.hardware.gyroscope.repository.GyroscopeRepository
import com.jj.domain.hardware.magneticfield.repository.MagneticFieldRepository
import com.jj.domain.hardware.general.SensorsRepository
import com.jj.domain.model.analysis.AnalysedSample
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