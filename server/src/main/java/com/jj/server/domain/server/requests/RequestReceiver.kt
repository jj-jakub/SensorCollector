package com.jj.server.domain.server.requests

import com.jj.server.domain.server.Endpoint
import com.jj.server.domain.server.requests.model.RequestResult

interface RequestReceiver {

    suspend fun receive(endpoint: Endpoint): RequestResult
}