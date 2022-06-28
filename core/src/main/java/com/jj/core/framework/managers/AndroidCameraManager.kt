package com.jj.core.framework.managers

import android.content.Context
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.google.common.util.concurrent.ListenableFuture
import com.jj.core.domain.managers.CameraManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class CustomLifecycle : LifecycleOwner {
    private val lifecycleRegistry: LifecycleRegistry = LifecycleRegistry(this)

    init {
        lifecycleRegistry.markState(Lifecycle.State.RESUMED)
    }

    fun doOnResume() {
        lifecycleRegistry.markState(Lifecycle.State.RESUMED)
    }

    override fun getLifecycle(): Lifecycle {
        return lifecycleRegistry
    }
}

class AndroidCameraManager(
    context: Context
) : CameraManager {

    private val cameraFuture: ListenableFuture<ProcessCameraProvider> = ProcessCameraProvider.getInstance(context)
    private val imageCapture = ImageCapture.Builder().build()
    private val executor = ContextCompat.getMainExecutor(context)
    private val selector = CameraSelector.Builder()
        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
        .build()

    private val mylifecycleOwner = CustomLifecycle()

    init {
        CoroutineScope(Dispatchers.Main).launch {
            ProcessCameraProvider.getInstance(context).get().bindToLifecycle(
                mylifecycleOwner,
                selector,
                imageCapture
            )
        }
    }

    private val callback = object : ImageCapture.OnImageCapturedCallback() {
        override fun onCaptureSuccess(image: ImageProxy) {
            super.onCaptureSuccess(image)
            Log.d("ABAB", "Success")
        }

        override fun onError(exception: ImageCaptureException) {
            super.onError(exception)
            Log.d("ABAB", "Error")
            exception.printStackTrace()
        }
    }

    override fun takePhoto(context: Context) {
        val outputFileOptions = ImageCapture.OutputFileOptions.Builder(File("aa")).build()
        CoroutineScope(Dispatchers.Main).launch {
            imageCapture.takePicture(executor, callback)
        }
    }
}