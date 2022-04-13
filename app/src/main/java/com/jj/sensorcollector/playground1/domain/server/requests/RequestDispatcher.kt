package com.jj.sensorcollector.playground1.domain.server.requests

interface RequestDispatcher {

    fun dispatchRequest(requestType: RequestType)
}