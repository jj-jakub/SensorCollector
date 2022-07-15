package com.jj.core.domain.status.camera

sealed class CameraStatus {
    object Available: CameraStatus()
    object Working: CameraStatus()
}