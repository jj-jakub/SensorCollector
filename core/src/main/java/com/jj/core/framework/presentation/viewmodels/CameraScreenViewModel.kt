package com.jj.core.framework.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jj.core.domain.managers.CameraManager
import kotlinx.coroutines.launch

class CameraScreenViewModel(
    private val cameraManager: CameraManager,
) : ViewModel() {

    fun registerCameraPreview(preview: androidx.camera.core.Preview) {
        cameraManager.registerCameraPreview(preview)
    }

    fun onTakePhotoClick() {
        viewModelScope.launch {
            cameraManager.takePhoto()
        }
    }
}