package com.example.allengineeringinone.ui.tools.data.service

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import android.util.Log
import com.example.allengineeringinone.ui.tools.data.repository.AudioRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AudioRecordingService @Inject constructor(
    @ApplicationContext private val context: Context,
    private val audioRepository: AudioRepository
) {

    private var currentFile: File? = null
    private var recorder: MediaRecorder? = null

    /**
     * Inicia la grabación en un archivo de salida específico.
     */
    fun startRecording(){
        stop()

        currentFile = audioRepository.createTempAudioFile()

        recorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MediaRecorder(context)
        } else {
            @Suppress("DEPRECATION")
            MediaRecorder()
        }.apply {
            try {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                setOutputFile(currentFile?.absolutePath)

                prepare()
                start()
            } catch (e: IOException) {
                Log.e("VOICE_DEBUG", "Falló la preparación de MediaRecorder", e)
            }
        }

    }


    /**
     * Detiene la grabación actual y devuelve el archivo grabado.
     */
    fun stop() {
        try {
            recorder?.stop()
        } catch (e: Exception) {
            Log.e("VOICE_RECORDER", "Error al detener MediaRecorder", e)
        } finally {
            recorder?.reset()
            recorder?.release()
            recorder = null
        }

        currentFile?.let {
            audioRepository.saveAudio(it)
            currentFile = null
        }
    }
}