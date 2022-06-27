package com.jj.server.data.server

import android.util.Log
import com.jj.core.domain.managers.VibrationManager

class DefaultRequestDispatcher(
    private val vibrationManager: VibrationManager
) : com.jj.server.domain.server.requests.RequestDispatcher {

    override fun dispatchRequest(requestType: com.jj.server.domain.server.requests.RequestType) {
        when (requestType) {
            com.jj.server.domain.server.requests.RequestType.HomeCalled -> Log.d("ABABS", "HomeCalled dispatch") // TODO Impl
            com.jj.server.domain.server.requests.RequestType.TakePhoto -> Log.d("ABABS", "TakePhoto dispatch") // TODO Impl
            com.jj.server.domain.server.requests.RequestType.Vibrate -> vibrationManager.vibrate(1000L)
        }
    }
}