package com.jj.sensorcollector.playground1.data.api

import android.util.Log
import com.jj.core.domain.api.AccelerometerAPI

class DefaultAccelerometerAPI : AccelerometerAPI {

    override suspend fun sendSample() {
        Log.d("ABAB", "DefaultAccelerometerService: Sample sent")
    }
}