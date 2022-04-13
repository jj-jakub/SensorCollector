package com.jj.sensorcollector.playground1.framework.server.requests

import com.jj.sensorcollector.playground1.domain.server.Endpoint
import com.jj.sensorcollector.playground1.domain.server.requests.RequestDispatcher
import com.jj.sensorcollector.playground1.domain.server.requests.RequestReceiver
import com.jj.sensorcollector.playground1.domain.server.requests.RequestType

class KtorRequestReceiver(
    private val requestDispatcher: RequestDispatcher
) : RequestReceiver {

    override fun receive(endpoint: Endpoint) {
        when (endpoint) {
            Endpoint.Home -> requestDispatcher.dispatchRequest(RequestType.HomeCalled)
            Endpoint.TakePhoto -> requestDispatcher.dispatchRequest(RequestType.TakePhoto)
            Endpoint.Vibrate -> requestDispatcher.dispatchRequest(RequestType.Vibrate)
        }
    }
}