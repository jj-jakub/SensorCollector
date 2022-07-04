package com.jj.core.data.managers

import android.util.Log
import com.jj.core.domain.coroutines.CoroutineScopeProvider
import com.jj.core.domain.managers.CameraManager
import com.jj.core.domain.managers.VibrationManager
import com.jj.core.domain.server.RemoteControlManager
import com.jj.core.domain.server.requests.RequestType
import kotlinx.coroutines.launch

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

    private suspend fun takePhoto(): Boolean = cameraManager.takePhoto()
}