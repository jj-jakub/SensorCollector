package com.jj.domain.api

interface AccelerometerAPI {
    suspend fun sendSample()
}