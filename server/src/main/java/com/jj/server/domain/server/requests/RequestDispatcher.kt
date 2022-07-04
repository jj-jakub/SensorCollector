package com.jj.server.domain.server.requests

import com.jj.core.domain.server.requests.RequestType
import com.jj.server.domain.server.requests.model.RequestResult

interface RequestDispatcher {

    suspend fun dispatchRequest(requestType: RequestType): RequestResult
}