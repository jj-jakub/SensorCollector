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
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Locale

class AndroidCameraManager(
    private val context: Context,
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

    private val callback = object : ImageCapture.OnImageSavedCallback {
        override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
            Log.d("ABAB", "onImageSaved, results: $outputFileResults")
        }

        override fun onError(exception: ImageCaptureException) {
            Log.d("ABAB", "onError, ", exception)
        }
    }

    // 1. Get instance before someone else takes it, i.e. view
    // 2. Don't pass preview if it is not used
    // TODO Try to return preview from here to other views? just to have getInstance in one place called once
    override fun takePhoto() {
        try {
            imageCapture.takePicture(getOutputOptions(), executor, callback)
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
        cameraXProvider.getCameraProvider().bindToLifecycle(
            lifecycleOwner,
            selector,
            imageCapture,
            useCase
        )
    }
}