package com.jj.core.framework.data.hardware.camera

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.provider.MediaStore
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.UseCase
import androidx.core.content.ContextCompat
import com.jj.domain.hardware.camera.model.CameraPhotoResult
import com.jj.domain.hardware.camera.model.CameraStatus
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow

class AndroidCameraManager(
    private val context: Context,
    private val cameraXProvider: CameraXProvider
) : CameraManager {

    private val _cameraStatus = MutableStateFlow<CameraStatus>(CameraStatus.Available)
    override val cameraStatus = _cameraStatus.asStateFlow()

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

    override suspend fun takePhoto() = callbackFlow {
        try {
            trySend(CameraPhotoResult.InProgress)
            _cameraStatus.value = CameraStatus.Working
            imageCapture.takePicture(
                getOutputOptions(),
                executor,
                object : ImageCapture.OnImageSavedCallback {
                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                        trySend(CameraPhotoResult.Success(outputFileResults.savedUri.toString()))
                        _cameraStatus.value = CameraStatus.Available
                    }

                    override fun onError(exception: ImageCaptureException) {
                        trySend(CameraPhotoResult.Failure)
                        _cameraStatus.value = CameraStatus.Available
                    }
                }
            )
        } catch (e: Exception) {
            trySend(CameraPhotoResult.Failure)
            _cameraStatus.value = CameraStatus.Available
        }

        awaitClose { channel.close() }
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