package com.example.allengineeringinone.ui.camera.data.service

import android.content.Context
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.core.UseCaseGroup
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.video.Recorder
import androidx.camera.video.VideoCapture
import androidx.lifecycle.LifecycleOwner
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.guava.await
import javax.inject.Inject

/**
 * Información: Esta clase se encarga de la vinculación de los casos de uso de la cámara.
 *
 * */
class CameraService @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private var cameraProvider: ProcessCameraProvider? = null

    suspend fun bindUseCases(
        lifecycleOwner: LifecycleOwner,
        cameraSelector: CameraSelector,
        preview: Preview,
        imageCapture: ImageCapture?,
        videoCapture: VideoCapture<Recorder>?
    ) {
        cameraProvider = ProcessCameraProvider.getInstance(context).await()

        try {
            cameraProvider?.unbindAll()

            val useCasesToBind = listOfNotNull(preview, imageCapture, videoCapture)

            if (useCasesToBind.isNotEmpty()) {
                cameraProvider?.bindToLifecycle(
                    lifecycleOwner,
                    cameraSelector,
                    *useCasesToBind.toTypedArray()
                )
            }
        }catch (
            e : Exception
        ){
            Log.e("CameraService", "Falló la vinculación de los casos de uso", e)
        }

    }

    fun releaseCamera() {
        cameraProvider?.unbindAll()
    }

}
