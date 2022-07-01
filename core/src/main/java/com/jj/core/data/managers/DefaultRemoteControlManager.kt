package com.jj.core.data.managers

import android.util.Log
import com.jj.core.domain.managers.CameraManager
import com.jj.core.domain.managers.VibrationManager
import com.jj.core.domain.server.RemoteControlManager
import com.jj.core.domain.server.requests.RequestType

class DefaultRemoteControlManager(
    private val vibrationManager: VibrationManager,
    private val cameraManager: CameraManager
) : RemoteControlManager {

    override fun receiveRequest(requestType: RequestType) {
        when (requestType) {
            RequestType.HomeCalled -> Log.d("ABABS", "HomeCalled receive") // TODO Impl
            RequestType.TakePhoto -> cameraManager.takePhoto()
            RequestType.Vibrate -> vibrationManager.vibrate(1000L)
        }
    }
}