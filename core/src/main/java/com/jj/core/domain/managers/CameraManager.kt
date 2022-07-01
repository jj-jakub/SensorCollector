package com.jj.core.domain.managers

import androidx.camera.core.UseCase

interface CameraManager {

    fun takePhoto()
    fun registerCameraPreview(useCase: UseCase)
}