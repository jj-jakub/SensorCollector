package com.jj.sensorcollector.playground1.data.server

import android.util.Log
import com.jj.sensors.domain.managers.VibrationManager
import com.jj.sensorcollector.playground1.domain.server.requests.RequestDispatcher
import com.jj.sensorcollector.playground1.domain.server.requests.RequestType

class DefaultRequestDispatcher(
    private val vibrationManager: VibrationManager
) : RequestDispatcher {

    override fun dispatchRequest(requestType: RequestType) {
        when (requestType) {
            RequestType.HomeCalled -> Log.d("ABABS", "HomeCalled dispatch") // TODO Impl
            RequestType.TakePhoto -> Log.d("ABABS", "TakePhoto dispatch") // TODO Impl
            RequestType.Vibrate -> vibrationManager.vibrate(1000L)
        }
    }
}