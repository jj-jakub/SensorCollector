package com.jj.sensorcollector.playground1.domain.server

sealed class Endpoint(val url: String) {
    object Home: Endpoint("/")
    object TakePhoto: Endpoint("/photo")
    object Vibrate: Endpoint("/vibrate")
}