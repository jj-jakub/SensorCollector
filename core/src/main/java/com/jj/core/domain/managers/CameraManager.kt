package com.jj.core.domain.managers

import androidx.camera.core.UseCase
import com.jj.core.domain.result.CameraPhotoResult
import kotlinx.coroutines.flow.Flow

interface CameraManager {

    suspend fun takePhoto(): Flow<CameraPhotoResult>
    fun registerCameraPreview(useCase: UseCase)
}