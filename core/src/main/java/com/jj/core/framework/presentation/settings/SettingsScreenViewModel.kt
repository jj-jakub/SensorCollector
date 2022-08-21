package com.jj.core.framework.presentation.settings

import androidx.lifecycle.ViewModel
import com.jj.core.domain.managers.CameraManager

class SettingsScreenViewModel(
    private val cameraManager: CameraManager,
) : ViewModel() {

    fun registerCameraPreview(preview: androidx.camera.core.Preview) {
        cameraManager.registerCameraPreview(preview)
    }
}