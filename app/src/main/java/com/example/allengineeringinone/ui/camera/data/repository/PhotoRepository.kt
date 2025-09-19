package com.example.allengineeringinone.ui.camera.data.repository

import android.net.Uri
import java.io.File

interface PhotoRepository {
    fun createTempPhotoFile(): File
    fun savePhotoToGallery(file: File): Uri?
}