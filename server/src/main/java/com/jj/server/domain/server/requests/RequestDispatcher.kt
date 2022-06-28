package com.jj.server.domain.server.requests

import com.jj.core.domain.server.requests.RequestType

interface RequestDispatcher {

    fun dispatchRequest(requestType: RequestType)
}