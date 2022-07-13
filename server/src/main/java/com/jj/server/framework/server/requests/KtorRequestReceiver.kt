package com.jj.server.framework.server.requests

import com.jj.core.domain.server.requests.RequestType
import com.jj.server.domain.server.Endpoint
import com.jj.server.domain.server.requests.RequestDispatcher
import com.jj.server.domain.server.requests.RequestReceiver
import com.jj.server.domain.server.requests.model.RequestResult

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