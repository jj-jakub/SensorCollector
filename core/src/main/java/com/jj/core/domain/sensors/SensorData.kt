package com.jj.core.domain.sensors

@Deprecated("Old implementation")
sealed class SensorData : ISensorData {
    data class AccelerometerData(val time: Long, val x: Float, val y: Float, val z: Float) : SensorData()
    data class GPSData(val time: Long, val lat: Double, val lng: Double) : SensorData()
}