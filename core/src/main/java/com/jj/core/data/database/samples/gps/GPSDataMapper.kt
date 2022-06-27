package com.jj.core.data.database.samples.gps

import com.jj.core.domain.sensors.SensorData.GPSData

fun GPSDataEntity.toGPSData() = GPSData(
        time = time,
        lat = lat,
        lng = lng,
)

fun GPSData.toGPSDataEntity() = GPSDataEntity(
        time = time,
        lat = lat,
        lng = lng,
)