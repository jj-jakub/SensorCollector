package com.jj.core.framework.managers

import android.content.Context
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.google.common.util.concurrent.ListenableFuture
import com.jj.core.domain.managers.CameraManager
import java.io.File

class AndroidCameraManager(
    context: Context
) : CameraManager {

    private val cameraFuture: ListenableFuture<ProcessCameraProvider> = ProcessCameraProvider.getInstance(context)
    private val imageCapture = ImageCapture.Builder().build()
    private val executor = ContextCompat.getMainExecutor(context)
    private val preview = Preview.Builder().build()
    private val previewView = PreviewView(context)
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
        preview.setSurfaceProvider(previewView.surfaceProvider)
        cameraFuture.get().bindToLifecycle(
            lifecycleOwner,
            selector,
            imageCapture,
            preview
        )
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

    override fun takePhoto() {
        val outputFileOptions = ImageCapture.OutputFileOptions.Builder(File("aa")).build()
        imageCapture.takePicture(executor, callback)
    }
}