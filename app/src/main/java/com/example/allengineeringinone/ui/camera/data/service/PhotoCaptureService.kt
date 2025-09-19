package com.example.allengineeringinone.ui.camera.data.service

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.core.content.ContextCompat
import com.example.allengineeringinone.ui.camera.data.repository.PhotoRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotoCaptureService @Inject constructor(
    @ApplicationContext private val context: Context,
    private val photoRepository: PhotoRepository
    // Podrías tener un PhotoRepository para guardar las fotos
) {

    private var currentFile: File? = null

    // La función principal. Recibe el caso de uso ImageCapture ya configurado.
    fun takePhoto(
        imageCapture: ImageCapture
    ) {

        // 1. Creamos la foto temporal y su configuración inicial de donde se guardara.
        currentFile = photoRepository.createTempPhotoFile();
        val outputFileOptions = ImageCapture.OutputFileOptions.Builder(currentFile!!).build()

        // 2. Llama a la función de CameraX
        imageCapture.takePicture(
            outputFileOptions,
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageSavedCallback {


                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    currentFile?.let { file ->
                        photoRepository.savePhotoToGallery(file)
                    }
                }

                override fun onError(exception: ImageCaptureException) {
                    Log.e("PHOTO_DEBUG", "Error al tomar la foto: ${exception.message}", exception)
                }
            }
        )
    }
}