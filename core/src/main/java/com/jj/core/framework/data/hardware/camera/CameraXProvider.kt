package com.jj.core.framework.data.hardware.camera

import android.content.Context
import androidx.camera.lifecycle.ProcessCameraProvider

class CameraXProvider(context: Context) {

    private val cameraXFuture = ProcessCameraProvider.getInstance(context)

    fun getCameraProvider(): ProcessCameraProvider = cameraXFuture.get()
}