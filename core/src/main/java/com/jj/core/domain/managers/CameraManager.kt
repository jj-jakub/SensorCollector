package com.jj.core.domain.managers

import androidx.camera.core.UseCase

interface CameraManager {

    suspend fun takePhoto(): Boolean
    fun registerCameraPreview(useCase: UseCase)
}