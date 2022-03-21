package com.jj.sensorcollector.data.sensors

import com.jj.sensorcollector.data.sensors.SensorControlEvent.LaunchingFailed
import com.jj.sensorcollector.data.sensors.SensorControlEvent.SuccessfullyLaunched
import com.jj.sensorcollector.domain.events.EventsCollector
import com.jj.sensorcollector.domain.result.DataResult
import com.jj.sensorcollector.domain.sensors.IGlobalSensorManager
import com.jj.sensorcollector.domain.sensors.interfaces.AccelerometerManager
import com.jj.sensorcollector.domain.sensors.interfaces.GPSManager
import java.util.Date

open class GlobalEvent(val eventType: String, val eventTime: Long)

sealed class SensorControlEvent(val type: String, val time: Long) : GlobalEvent(eventType = type, eventTime = time) {
    data class SuccessfullyLaunched(val t: Long) : SensorControlEvent(type = "SuccessfullyLaunched", time = t)
    data class LaunchingFailed(val t: Long, val exception: Exception) : SensorControlEvent(type = "LaunchingFailed", time = t)
}

class GlobalSensorManager(
        private val accelerometerManager: AccelerometerManager,
        private val gpsManager: GPSManager,
        private val eventsCollector: EventsCollector
) : IGlobalSensorManager {

    override fun startAccelerometer(): DataResult<SensorControlEvent> = invokeAndSaveEvent {
        accelerometerManager.start()
    }

    override fun startGPS(): DataResult<SensorControlEvent> = invokeAndSaveEvent {
        gpsManager.start()
    }

    override fun stopAccelerometer(): DataResult<SensorControlEvent> = invokeAndSaveEvent {
        accelerometerManager.stop()
    }

    override fun stopGPS(): DataResult<SensorControlEvent> = invokeAndSaveEvent {
        gpsManager.stop()
    }

    private fun invokeAndSaveEvent(block: () -> Unit): DataResult<SensorControlEvent> {
        val result = try {
            block()
            SuccessfullyLaunched(Date().time)
        } catch (e: Exception) {
            LaunchingFailed(Date().time, e)
        }

        handleResult(result)

        return when (result) {
            is SuccessfullyLaunched -> DataResult.Success(result)
            is LaunchingFailed -> DataResult.Error(result.exception)
        }
    }

    private fun handleResult(result: SensorControlEvent) {
        eventsCollector.onEvent(result)
    }
}