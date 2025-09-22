package com.example.allengineeringinone.ui.tools.data.repository

import android.net.Uri
import java.io.File

interface AudioRepository {
    fun createTempAudioFile(): File
    fun saveAudio(audioFile: File) : Uri?
}