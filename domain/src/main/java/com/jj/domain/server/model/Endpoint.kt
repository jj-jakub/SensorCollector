package com.jj.domain.server.model

sealed class Endpoint(val url: String) {
    object Home: Endpoint("/")
    object TakePhoto: Endpoint("/photo")
    object Vibrate: Endpoint("/vibrate")
}