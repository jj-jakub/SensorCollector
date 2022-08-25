package com.jj.domain.server

import com.jj.domain.server.model.Endpoint
import com.jj.domain.server.model.RequestResult

interface RequestReceiver {
    suspend fun receive(endpoint: Endpoint): RequestResult
}