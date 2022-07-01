package com.jj.core.framework.managers

import android.content.Context
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.core.UseCase
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.jj.core.domain.managers.CameraManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AndroidCameraManager(
    context: Context,
    private val cameraXProvider: CameraXProvider
) : CameraManager {

    private val imageCapture = ImageCapture.Builder().build()
    private val executor = ContextCompat.getMainExecutor(context)
    private val selector = CameraSelector.Builder()
        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
        .build()

    private val lifecycleOwner = object : LifecycleOwner {
        override fun getLifecycle(): Lifecycle {
            return object : Lifecycle() {
                override fun addObserver(observer: LifecycleObserver) {
                    /* no-op */
                }

                override fun removeObserver(observer: LifecycleObserver) {
                    /* no-op */
                }

                override fun getCurrentState(): State {
                    return State.RESUMED
                }
            }
        }
    }

    init {
        cameraXProvider.getCameraProvider().bindToLifecycle(
            lifecycleOwner,
            selector,
            imageCapture
        )
    }

    private val callback = object : ImageCapture.OnImageCapturedCallback() {
        override fun onCaptureSuccess(image: ImageProxy) {
            super.onCaptureSuccess(image)
            Log.e("ABAB", "onCaptureSuccess")
            image.close()
        }

        override fun onError(exception: ImageCaptureException) {
            super.onError(exception)
            exception.printStackTrace()
        }
    }

    // 1. Get instance before someone else takes it, i.e. view
    // 2. Don't pass preview if it is not used
    // TODO Try to return preview from here to other views? just to have getInstance in one place called once
    override fun takePhoto() {
        try {
            imageCapture.takePicture(executor, callback)
        } catch (e: Exception) {
            Log.e("ABAB", "Camera launcher exception")
        }
    }

    override fun registerCameraPreview(useCase: UseCase) {
        cameraXProvider.getCameraProvider().bindToLifecycle(
            lifecycleOwner,
            selector,
            imageCapture,
            useCase
        )
    }
}