package com.jj.core.domain.result

sealed class CameraPhotoResult {
    object InProgress : CameraPhotoResult()
    object Success : CameraPhotoResult()
    object Failure : CameraPhotoResult()
}
