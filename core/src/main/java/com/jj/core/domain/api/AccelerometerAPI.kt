package com.jj.core.domain.api

interface AccelerometerAPI {

    suspend fun sendSample()
}