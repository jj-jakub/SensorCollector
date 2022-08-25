package com.jj.domain.server

import com.jj.domain.server.model.RequestType
import com.jj.domain.server.model.RequestResult

interface RequestDispatcher {
    suspend fun dispatchRequest(requestType: RequestType): RequestResult
}