package com.jj.sensorcollector.data.database.samples

import com.jj.sensorcollector.domain.sensors.SensorData.GPSData

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