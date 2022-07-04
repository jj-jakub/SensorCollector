package com.jj.server.domain.server.requests.model

sealed class RequestResult {
    object Success: RequestResult()
    object Failure: RequestResult()
}
