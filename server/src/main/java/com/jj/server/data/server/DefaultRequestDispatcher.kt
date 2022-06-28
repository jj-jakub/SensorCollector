package com.jj.server.data.server

import com.jj.core.domain.server.RemoteControlManager
import com.jj.core.domain.server.requests.RequestType
import com.jj.server.domain.server.requests.RequestDispatcher

class DefaultRequestDispatcher(
    private val remoteControlManager: RemoteControlManager
) : RequestDispatcher {

    override fun dispatchRequest(requestType: RequestType) {
        when (requestType) {
            RequestType.HomeCalled -> remoteControlManager.receiveRequest(requestType)
            RequestType.TakePhoto -> remoteControlManager.receiveRequest(requestType)
            RequestType.Vibrate -> remoteControlManager.receiveRequest(requestType)
        }
    }
}