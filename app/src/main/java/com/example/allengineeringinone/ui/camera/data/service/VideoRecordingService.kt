package com.example.allengineeringinone.ui.camera.data.service

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.video.FallbackStrategy
import androidx.camera.video.FileOutputOptions
import androidx.camera.video.Quality
import androidx.camera.video.QualitySelector
import androidx.camera.video.Recorder
import androidx.camera.video.Recording
import androidx.camera.video.VideoCapture
import androidx.camera.video.VideoRecordEvent
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.example.allengineeringinone.ui.camera.data.repository.VideoRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.guava.await
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VideoRecordingService @Inject constructor(
    @ApplicationContext private val context: Context,
    private val videoRepository: VideoRepository
){
    private var activeRecording: Recording? = null
    private var currentOutputFile: File? = null
    private var cameraProvider: ProcessCameraProvider? = null

    @SuppressLint("MissingPermission")
    fun startRecording(videoCapture: VideoCapture<Recorder>) {
        Log.d("DEBUG_VIDEO", "Service: startRecording() llamado.") // <-- LOG 7

        val currentVideoCapture = videoCapture ?: return

        // Detenemos cualquier grabación anterior
        stopRecording()

        // Creamos el archivo de salida
        currentOutputFile = videoRepository.createTempVideoFile()
        val outputOptions = FileOutputOptions.Builder(currentOutputFile!!).build()

        Log.d("DEBUG_VIDEO", "Service: Preparando y comenzando la grabación...") // <-- LOG 9

        // ¡Empezamos a grabar!
        activeRecording = currentVideoCapture.output
            .prepareRecording(context, outputOptions)
            .withAudioEnabled() // Habilitamos el audio
            .start(ContextCompat.getMainExecutor(context)) { event ->
                // Este bloque se llama con eventos de la grabación
                when (event) {
                    is VideoRecordEvent.Finalize -> {
                        if (event.hasError()) {
                            Log.e("VideoRecordingService", "Grabación finalizada con error: ${event.error}")
                        } else {
                            Log.i("VideoRecordingService", "Grabación finalizada con éxito. URI: ${event.outputResults.outputUri}")
                            // Aquí podrías llamar al repositorio para mover el video a la galería

                            currentOutputFile?.let { file ->
                                videoRepository.saveVideoToGallery(file)
                            }
                        }
                    }
                }
            }
    }

    fun stopRecording() {
        activeRecording?.stop()
        activeRecording = null
    }

    fun releaseCamera() {
        cameraProvider?.unbindAll()
    }
}