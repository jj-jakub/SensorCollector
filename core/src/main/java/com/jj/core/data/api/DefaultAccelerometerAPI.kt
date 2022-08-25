package com.jj.core.data.api

import android.util.Log
import com.jj.domain.api.AccelerometerAPI

class DefaultAccelerometerAPI : AccelerometerAPI {

    override suspend fun sendSample() {
        Log.d("ABAB", "DefaultAccelerometerService: Sample sent")
    }
}