package com.jj.sensorcollector.playground1.data.api

import android.util.Log
import com.jj.sensorcollector.playground1.domain.api.AccelerometerAPI

class DefaultAccelerometerAPI : AccelerometerAPI {

    override suspend fun sendSample() {
        Log.d("ABAB", "DefaultAccelerometerService: Sample sent")
    }
}