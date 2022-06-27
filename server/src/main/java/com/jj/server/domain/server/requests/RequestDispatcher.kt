package com.jj.server.domain.server.requests

interface RequestDispatcher {

    fun dispatchRequest(requestType: RequestType)
}