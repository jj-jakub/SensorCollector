package com.jj.sensorcollector.playground1.domain.api

interface AccelerometerAPI {

    suspend fun sendSample()
}