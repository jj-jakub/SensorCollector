package com.jj.core.framework.managers

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.provider.MediaStore
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
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AndroidCameraManager(
    private val context: Context,
    private val cameraXProvider: CameraXProvider
) : CameraManager {

    private val cameraXLifecycleOwner = CameraXLifecycleOwner()
    private val imageCapture = ImageCapture.Builder().build()
    private val executor = ContextCompat.getMainExecutor(context)
    private val selector = CameraSelector.Builder()
        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
        .build()

    init {
        cameraXProvider.getCameraProvider().bindToLifecycle(
            cameraXLifecycleOwner,
            selector,
            imageCapture
        )
    }

    private val callback = object : ImageCapture.OnImageSavedCallback {
        override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
            Log.d("ABAB", "onImageSaved, results: $outputFileResults")
        }

        override fun onError(exception: ImageCaptureException) {
            Log.d("ABAB", "onError, ", exception)
        }
    }

    override suspend fun takePhoto() = suspendCoroutine<Boolean> { continuation ->
        try {
            imageCapture.takePicture(getOutputOptions(), executor, object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    Log.d("ABAB", "onImageSaved, results: $outputFileResults")
                    continuation.resume(true)
                }

                override fun onError(exception: ImageCaptureException) {
                    continuation.resume(false)
                    Log.d("ABAB", "onError, ", exception)
                }
            })
        } catch (e: Exception) {
            Log.e("ABAB", "Camera launcher exception")
        }
    }

    private fun getOutputOptions() =
        ImageCapture.OutputFileOptions.Builder(
            context.contentResolver,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            getContentValues()
        ).build()

    private fun getContentValues(): ContentValues {
        val name = System.currentTimeMillis()
        return ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image")
            }
        }
    }

    override fun registerCameraPreview(useCase: UseCase) {
        val provider = cameraXProvider.getCameraProvider()
        provider.unbindAll()
        provider.bindToLifecycle(
            cameraXLifecycleOwner,
            selector,
            imageCapture,
            useCase
        )
    }
}