package com.jj.core.domain.result

sealed class CameraPhotoResult {
    object InProgress : CameraPhotoResult()
    data class Success(val imageUri: String) : CameraPhotoResult()
    object Failure : CameraPhotoResult()
}
