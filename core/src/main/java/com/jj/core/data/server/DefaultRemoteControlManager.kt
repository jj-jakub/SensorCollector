package com.jj.core.data.server

import android.util.Log
import com.jj.core.framework.data.hardware.camera.CameraManager
import com.jj.domain.hardware.camera.model.CameraPhotoResult
import com.jj.domain.hardware.vibration.managers.VibrationManager
import com.jj.domain.server.RemoteControlManager
import com.jj.domain.server.model.RequestType
import kotlinx.coroutines.flow.first

class DefaultRemoteControlManager(
    private val vibrationManager: VibrationManager,
    private val cameraManager: CameraManager
) : RemoteControlManager {

    override suspend fun receiveRequest(requestType: RequestType): Boolean =
        when (requestType) {
            RequestType.HomeCalled -> onHomeCalled()
            RequestType.TakePhoto -> takePhoto()
            RequestType.Vibrate -> vibrationManager.vibrate(1000L)
        }

    private fun onHomeCalled(): Boolean {
        Log.d("ABABS", "HomeCalled receive") // TODO Impl
        return true
    }

    private suspend fun takePhoto(): Boolean = cameraManager.takePhoto().first { result ->
        result == CameraPhotoResult.Failure || result is CameraPhotoResult.Success
    } is CameraPhotoResult.Success
}

