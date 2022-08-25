package com.jj.domain.server.model

sealed class RequestResult {
    object Success: RequestResult()
    object Failure: RequestResult()
}
