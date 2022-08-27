package com.jj.core.data.database.gps.analysis.path

import com.jj.domain.hardware.gps.model.GPSPathData
import java.util.Date

fun GPSPathDataEntity.toGPSPathData() = GPSPathData(
    id = this.id,
    startDate = Date(this.startDate),
    endDate = this.endDate?.let { Date(it) },
    startLatitude = this.startLatitude,
    endLatitude = this.endLatitude,
    startLongitude = this.startLongitude,
    endLongitude = this.endLongitude,
)

fun GPSPathData.toGPSPathDataEntity() = GPSPathDataEntity(
    id = this.id,
    startDate = this.startDate.time,
    endDate = this.endDate?.time,
    startLatitude = this.startLatitude,
    endLatitude = this.endLatitude,
    startLongitude = this.startLongitude,
    endLongitude = this.endLongitude,
)