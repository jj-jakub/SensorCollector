package com.jj.domain.server.model

sealed class RequestType {
    object HomeCalled: RequestType()
    object TakePhoto : RequestType()
    object Vibrate : RequestType()
}