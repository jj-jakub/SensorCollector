package com.jj.domain.hardware.gps.usecase

import com.jj.domain.base.usecase.UseCase
import com.jj.domain.hardware.gps.repository.GPSRepository
import com.jj.domain.hardware.model.SensorData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class StartGPSCollection(
    private val gpsRepository: GPSRepository,
) : UseCase<Unit, Flow<SensorData>> {

    override suspend fun invoke(param: Unit): Flow<SensorData> = flow {
        gpsRepository.collectGPSSamples().collect {
            if (it is SensorData.GPSSample || it is SensorData.Error) {
                emit(it)
            }
        }
    }
}