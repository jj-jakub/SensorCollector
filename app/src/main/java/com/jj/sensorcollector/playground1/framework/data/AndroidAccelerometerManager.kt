package com.jj.sensorcollector.playground1.framework.data

import android.content.Context
import android.hardware.Sensor
import com.jj.sensorcollector.playground1.domain.managers.AccelerometerManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class AndroidAccelerometerManager(context: Context) : AccelerometerManager, AndroidSmartSensorManager(
    context = context,
    sensorType = Sensor.TYPE_ACCELEROMETER,
    scope = CoroutineScope(Dispatchers.IO) // TODO Inject
)