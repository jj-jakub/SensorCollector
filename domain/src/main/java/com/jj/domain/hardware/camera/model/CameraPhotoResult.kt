package com.jj.domain.hardware.camera.model

sealed class CameraPhotoResult {
    object InProgress : CameraPhotoResult()
    data class Success(val imageUri: String) : CameraPhotoResult()
    object Failure : CameraPhotoResult()
}
