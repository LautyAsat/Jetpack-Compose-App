package com.example.allengineeringinone.ui.camera.data.repository

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VideoRepositoryImpl(
    private val context: Context
) : VideoRepository {

    /**
     * Crea un archivo de video temporal en el directorio de caché de la app.
     * Este archivo es privado y se borrará cuando se limpie la caché.
     */
    override fun createTempVideoFile(): File {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir = context.cacheDir
        // Crea un archivo como "VIDEO_20250916_103500.mp4" en la caché
        return File.createTempFile("VIDEO_${timestamp}", ".mp4", storageDir)
    }

    /**
     * Guarda el archivo de video final en la galería pública del dispositivo (en la carpeta "Movies").
     */
    override fun saveVideoToGallery(videoFile: File): Uri? {
        Log.d("DEBUG_VIDEO", "Repository: Iniciando guardado para el archivo: ${videoFile.name}") // <-- LOG 1

        val contentResolver = context.contentResolver
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val name = "VIDEO_$timestamp.mp4"

        // 1. Preparamos los metadatos del video para la galería
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "video/mp4")
            // Indicamos que se guarde en la carpeta "Movies"
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_MOVIES)
            }
        }

        return try {
            // 2. Creamos una entrada en MediaStore y obtenemos su URI
            val uri = contentResolver.insert(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )

            if (uri == null) {
                Log.e("DEBUG_VIDEO", "Repository: ERROR - contentResolver.insert() devolvió null.") // <-- LOG 2
                return null
            }

            Log.d("DEBUG_VIDEO", "Repository: URI creada en MediaStore: $uri. Copiando bytes...") // <-- LOG 3

            uri?.let { destinationUri ->
                // 3. Abrimos un "canal" para escribir en el nuevo archivo de la galería
                contentResolver.openOutputStream(destinationUri)?.use { outputStream ->
                    // 4. Abrimos el archivo temporal para leerlo
                    videoFile.inputStream().use { inputStream ->
                        // 5. Copiamos el contenido del archivo temporal al archivo final
                        inputStream.copyTo(outputStream)
                    }
                }

                // 6. (Opcional) Borramos el archivo temporal una vez copiado
                videoFile.delete()
            }

            Log.i("DEBUG_VIDEO", "Repository: ¡Video guardado en galería y temporal borrado con éxito!") // <-- LOG 4

            uri // Devolvemos el URI del video guardado en la galería

        } catch (e: Exception) {
            Log.e("DEBUG_VIDEO", "Repository: EXCEPCIÓN al guardar en galería.", e) // <-- LOG 5

            e.printStackTrace()
            null
        }
    }
}