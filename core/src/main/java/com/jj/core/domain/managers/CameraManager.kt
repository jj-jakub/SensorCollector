package com.jj.core.domain.managers

import androidx.camera.core.UseCase
import com.jj.core.domain.result.CameraPhotoResult
import com.jj.core.domain.status.camera.CameraStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface CameraManager {

    val cameraStatus: StateFlow<CameraStatus>
    suspend fun takePhoto(): Flow<CameraPhotoResult>
    fun registerCameraPreview(useCase: UseCase)
}