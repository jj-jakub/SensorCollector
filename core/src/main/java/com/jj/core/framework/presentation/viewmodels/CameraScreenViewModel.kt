package com.jj.core.framework.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jj.core.domain.managers.CameraManager
import com.jj.core.domain.result.CameraPhotoResult
import com.jj.core.domain.status.camera.CameraStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CameraScreenViewModel(
    private val cameraManager: CameraManager
) : ViewModel() {

    // TODO Should be obtained from Manager
    private val _cameraStatus = MutableStateFlow<CameraStatus>(CameraStatus.Available)
    val cameraStatus = _cameraStatus.asStateFlow()

    private val _takePhotoButtonActive = MutableStateFlow(true)
    val takePhotoButtonActive = _takePhotoButtonActive.asStateFlow()

    private val _lastImageUri = MutableStateFlow("")
    val lastImageUri = _lastImageUri.asStateFlow()

    fun registerCameraPreview(preview: androidx.camera.core.Preview) {
        cameraManager.registerCameraPreview(preview)
    }

    fun onTakePhotoClick() {
        viewModelScope.launch {
            cameraManager.takePhoto().collect {
                onCameraPhotoResult(it)
            }
        }
    }

    private fun onCameraPhotoResult(cameraPhotoResult: CameraPhotoResult) {
        when (cameraPhotoResult) {
            CameraPhotoResult.Failure -> {
                // TODO Show error
                _takePhotoButtonActive.value = true
                _cameraStatus.value = CameraStatus.Available
            }
            CameraPhotoResult.InProgress -> {
                _takePhotoButtonActive.value = false
                _cameraStatus.value = CameraStatus.Working
            }
            is CameraPhotoResult.Success -> {
                _takePhotoButtonActive.value = true
                _lastImageUri.value = cameraPhotoResult.imageUri
                _cameraStatus.value = CameraStatus.Available
            }
        }
    }
}