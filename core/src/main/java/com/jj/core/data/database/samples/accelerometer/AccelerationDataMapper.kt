package com.jj.core.data.database.samples.accelerometer

import com.fluffycat.sensorsmanager.data.AccelerationDataEntity
import com.jj.core.domain.sensors.SensorData.AccelerometerData

fun AccelerationDataEntity.toAccelerometerData() = AccelerometerData(
        x = x,
        y = y,
        z = z,
        time = time
)

fun AccelerometerData.toAccelerationDataEntity() = AccelerationDataEntity(
        x = x,
        y = y,
        z = z,
        time = time
)