package com.jj.sensorcollector.playground1.domain

sealed class SensorData {
    data class AccSample(val x: Float?, val y: Float?, val z: Float?): SensorData()
    data class Error(val msg: String, val e: Exception?): SensorData()
}