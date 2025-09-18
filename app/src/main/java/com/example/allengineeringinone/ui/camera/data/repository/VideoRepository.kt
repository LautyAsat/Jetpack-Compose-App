package com.example.allengineeringinone.ui.camera.data.repository

import android.net.Uri
import java.io.File

interface VideoRepository {
    fun createTempVideoFile(): File
    fun saveVideoToGallery(videoFile: File): Uri?
}