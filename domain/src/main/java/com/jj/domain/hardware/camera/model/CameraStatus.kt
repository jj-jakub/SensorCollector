package com.jj.domain.hardware.camera.model

sealed class CameraStatus {
    object Available: CameraStatus()
    object Working: CameraStatus()
}