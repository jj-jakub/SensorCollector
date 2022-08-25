package com.jj.core.framework.data.hardware.camera

import androidx.camera.core.UseCase
import com.jj.domain.hardware.camera.model.CameraPhotoResult
import com.jj.domain.hardware.camera.model.CameraStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface CameraManager {

    val cameraStatus: StateFlow<CameraStatus>
    suspend fun takePhoto(): Flow<CameraPhotoResult>
    fun registerCameraPreview(useCase: UseCase) // TODO Can't move to Domain because of this Android UseCase
}