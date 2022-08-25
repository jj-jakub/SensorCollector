package com.jj.server.data.server

import com.jj.domain.server.model.RequestType
import com.jj.domain.server.model.Endpoint
import com.jj.domain.server.RequestDispatcher
import com.jj.domain.server.RequestReceiver
import com.jj.domain.server.model.RequestResult

class KtorRequestReceiver(
    private val requestDispatcher: RequestDispatcher
) : RequestReceiver {

    override suspend fun receive(endpoint: Endpoint): RequestResult =
        when (endpoint) {
            Endpoint.Home -> requestDispatcher.dispatchRequest(RequestType.HomeCalled)
            Endpoint.TakePhoto -> requestDispatcher.dispatchRequest(RequestType.TakePhoto)
            Endpoint.Vibrate -> requestDispatcher.dispatchRequest(RequestType.Vibrate)
        }
}