package com.jj.core.data.managers

import android.util.Log
import com.jj.core.domain.managers.VibrationManager
import com.jj.core.domain.server.RemoteControlManager
import com.jj.core.domain.server.requests.RequestType

class DefaultRemoteControlManager(
    private val vibrationManager: VibrationManager
) : RemoteControlManager {

    override fun receiveRequest(requestType: RequestType) {
        when (requestType) {
            RequestType.HomeCalled -> Log.d("ABABS", "HomeCalled receive") // TODO Impl
            RequestType.TakePhoto -> Log.d("ABABS", "TakePhoto receive") // TODO Impl
            RequestType.Vibrate -> vibrationManager.vibrate(1000L)
        }
    }
}