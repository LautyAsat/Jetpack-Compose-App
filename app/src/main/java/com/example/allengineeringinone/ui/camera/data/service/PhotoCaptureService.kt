package com.example.allengineeringinone.ui.camera.data.service

import android.content.Context
import android.net.Uri
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotoCaptureService @Inject constructor(
    @ApplicationContext private val context: Context,
    // Podrías tener un PhotoRepository para guardar las fotos
) {
    // La función principal. Recibe el caso de uso ImageCapture ya configurado.
    fun takePhoto(
        imageCapture: ImageCapture,
        onPhotoCaptured: (Uri) -> Unit,
        onError: (Exception) -> Unit
    ) {
        // 1. Configura dónde se guardará la foto.
        val outputFileOptions = ImageCapture.OutputFileOptions.Builder(
            // Aquí usarías tu PhotoRepository para crear un archivo temporal
            File(context.cacheDir, "temp_photo.jpg")
        ).build()

        // 2. Llama a la función de CameraX
        imageCapture.takePicture(
            outputFileOptions,
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    outputFileResults.savedUri?.let {
                        onPhotoCaptured(it)
                    }
                }

                override fun onError(exception: ImageCaptureException) {
                    onError(exception)
                }
            }
        )
    }
}