package com.jj.core.domain.server.requests

sealed class RequestType {
    object HomeCalled: RequestType()
    object TakePhoto : RequestType()
    object Vibrate : RequestType()
}