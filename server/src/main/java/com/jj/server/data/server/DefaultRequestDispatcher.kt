package com.jj.server.data.server

import com.jj.core.domain.server.RemoteControlManager
import com.jj.core.domain.server.requests.RequestType
import com.jj.server.domain.server.requests.RequestDispatcher
import com.jj.server.domain.server.requests.model.RequestResult

class DefaultRequestDispatcher(
    private val remoteControlManager: RemoteControlManager
) : RequestDispatcher {

    override suspend fun dispatchRequest(requestType: RequestType): RequestResult {
        val isSuccess = when (requestType) {
            RequestType.HomeCalled -> remoteControlManager.receiveRequest(requestType)
            RequestType.TakePhoto -> remoteControlManager.receiveRequest(requestType)
            RequestType.Vibrate -> remoteControlManager.receiveRequest(requestType)
        }

        return if (isSuccess) RequestResult.Success
        else RequestResult.Failure
    }
}