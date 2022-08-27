package com.jj.domain.hardware.gps.usecase.path

import com.jj.domain.base.usecase.UseCase
import com.jj.domain.hardware.gps.model.GPSPathData
import com.jj.domain.hardware.gps.repository.PathRepository
import com.jj.domain.time.TimeProvider
import java.util.Date

class StartPath(
    private val pathRepository: PathRepository,
    private val timeProvider: TimeProvider,
) : UseCase<Unit, Int> {

    override suspend fun invoke(param: Unit): Int {
        val gpsPathData = GPSPathData(
            id = null,
            startDate = Date(timeProvider.getNowMillis()),
            endDate = null,
            startLatitude = null,
            endLatitude = null,
            startLongitude = null,
            endLongitude = null,
        )
        return pathRepository.insertData(gpsPathData)
    }
}