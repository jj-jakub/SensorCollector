package com.jj.server.framework.server.requests

class KtorRequestReceiver(
    private val requestDispatcher: com.jj.server.domain.server.requests.RequestDispatcher
) : com.jj.server.domain.server.requests.RequestReceiver {

    override fun receive(endpoint: com.jj.server.domain.server.Endpoint) {
        when (endpoint) {
            com.jj.server.domain.server.Endpoint.Home -> requestDispatcher.dispatchRequest(com.jj.server.domain.server.requests.RequestType.HomeCalled)
            com.jj.server.domain.server.Endpoint.TakePhoto -> requestDispatcher.dispatchRequest(com.jj.server.domain.server.requests.RequestType.TakePhoto)
            com.jj.server.domain.server.Endpoint.Vibrate -> requestDispatcher.dispatchRequest(com.jj.server.domain.server.requests.RequestType.Vibrate)
        }
    }
}