package com.jj.sensorcollector.playground1.domain.server.requests

sealed class RequestType {
    object HomeCalled: RequestType()
    object TakePhoto : RequestType()
    object Vibrate : RequestType()
}