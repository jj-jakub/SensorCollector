package com.jj.sensorcollector.framework.sensors

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.jj.sensorcollector.data.sensors.AccelerometerDataCollector
import com.jj.sensorcollector.domain.sensors.SensorData.AccelerometerData
import com.jj.sensorcollector.domain.sensors.interfaces.AccelerometerManager
import java.util.Date

class AndroidAccelerometerManager(
        private val context: Context,
        private val accelerometerDataCollector: AccelerometerDataCollector
) : AccelerometerManager {

    private val sensorListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            accelerometerDataCollector.onDataReceived(AccelerometerData(Date().time, event.values[0], event.values[1], event.values[2]))
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
//            onAdditionalDataChanged(accuracy)
        }
    }

//    init {
//        CoroutineScope(Dispatchers.IO).launch {
//            sensorDataFlow.subscriptionCount.collect {
//                if (sensorDataFlow.subscriptionCount.value == 0) {
//                    unregisterListener()
//                }
//            }
//        }
//    }
//
//    override fun observeSensorValues(): SharedFlow<AccelerometerData> {
//        if (sensorDataFlow.subscriptionCount.value == 0) {
//            registerListener()
//        }
//        return sensorDataFlow.asSharedFlow()
//    }

    override fun start() {
        val sensorManager: SensorManager? = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager?
        val sensor = sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager?.registerListener(sensorListener, sensor, SensorManager.SENSOR_DELAY_GAME)
    }

    override fun stop() {
        val sensorManager: SensorManager? = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager?
        sensorManager?.unregisterListener(sensorListener)
    }
}