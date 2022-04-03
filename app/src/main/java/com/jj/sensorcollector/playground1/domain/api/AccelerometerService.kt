package com.jj.sensorcollector.playground1.domain.api

interface AccelerometerService {

    suspend fun sendSample()
}