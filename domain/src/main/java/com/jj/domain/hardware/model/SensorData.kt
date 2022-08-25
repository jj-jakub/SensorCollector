package com.jj.domain.hardware.model

sealed class SensorData {
    data class AccSample(val x: Float?, val y: Float?, val z: Float?): SensorData()
    data class GyroscopeSample(val x: Float?, val y: Float?, val z: Float?): SensorData()
    data class MagneticFieldSample(val x: Float?, val y: Float?, val z: Float?): SensorData()
    data class GPSSample(val latitude: Double, val longitude: Double): SensorData()
    data class Error(val errorType: ErrorType, val e: Exception?): SensorData()

    sealed class ErrorType(val errorCause: String) {
        class InitializationFailure(errorCause: String) : ErrorType(errorCause)
        class AnalysisFailure(errorCause: String) : ErrorType(errorCause)
    }
}