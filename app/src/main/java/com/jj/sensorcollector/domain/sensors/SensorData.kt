package com.jj.sensorcollector.domain.sensors

sealed class SensorData : ISensorData {
    data class AccelerometerData(val x: Float, val y: Float, val z: Float) : SensorData()
    data class GPSData(val lat: Long, val lng: Long) : SensorData()
}