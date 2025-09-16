package com.example.allengineeringinone.ui.tools.data.service

import android.content.Context
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class FlashlightService @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    private var cameraId: String? = null

    init {
        cameraId = findCameraWithFlash()
    }

    private fun findCameraWithFlash(): String? {
        return try {
            // Recorremos todas las cámaras disponibles en el dispositivo
            for (id in cameraManager.cameraIdList) {
                val characteristics = cameraManager.getCameraCharacteristics(id)
                val hasFlash = characteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE)
                val facing = characteristics.get(CameraCharacteristics.LENS_FACING)

                // Si tiene flash y es la cámara trasera, la hemos encontrado
                if (hasFlash == true && facing == CameraCharacteristics.LENS_FACING_BACK) {
                    return id
                }
            }
            null // No se encontró ninguna cámara con flash
        } catch (e: CameraAccessException) {
            Log.e("FlashlightService", "Error al acceder a la cámara", e)
            null
        }
    }

    fun turnOn() {
        if (cameraId == null) return
        try {
            cameraManager.setTorchMode(cameraId!!, true)
        } catch (e: CameraAccessException) {
            Log.e("FlashlightService", "No se pudo encender el flash", e)
        }
    }

    fun turnOff() {
        if (cameraId == null) return
        try {
            cameraManager.setTorchMode(cameraId!!, false)
        } catch (e: CameraAccessException) {
            Log.e("FlashlightService", "No se pudo apagar el flash", e)
        }
    }
}