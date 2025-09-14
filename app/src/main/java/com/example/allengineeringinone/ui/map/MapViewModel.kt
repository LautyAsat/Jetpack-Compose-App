package com.example.allengineeringinone.ui.map

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.allengineeringinone.ui.map.data.model.MapMarker
import com.example.allengineeringinone.ui.map.data.repository.MapRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.CancellationTokenSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val repository: MapRepository,
    private val fusedLocationClient: FusedLocationProviderClient
) : ViewModel(){

    private val viewModelState = MutableStateFlow(MapUIState())
    val uiState: StateFlow<MapUIState> = viewModelState.asStateFlow()

    init {
        loadMarkers()
    }

    private fun loadMarkers() {
        viewModelScope.launch {
            repository.getMarkers().collectLatest { result ->
                result.onSuccess { markers ->
                    viewModelState.value = viewModelState.value.copy(markers = markers)
                }.onFailure { error ->
                    viewModelState.value = viewModelState.value.copy(errorMessage = error.message)
                }
            }
        }
    }

    fun addMarker() {
        try {

            fusedLocationClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                CancellationTokenSource().token // Para cancelar la petición si es necesario
            ).addOnSuccessListener { location ->

                if(location != null){
                    viewModelScope.launch {
                        repository.saveMarker(location.latitude, location.longitude).onFailure { error ->
                            viewModelState.value = viewModelState.value.copy(errorMessage = error.message)
                        }
                    }
                }
            }

        }catch (e: SecurityException){
            Log.e("map", "Error al guardar el marcador", e)

            viewModelState.value = viewModelState.value.copy(
                permissionStatus = PermissionStatus.DENIED,
                errorMessage = "Permiso de ubicación denegado."
            )
        }


    }

    fun fetchInitialLocation() {
        try {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    viewModelState.value = viewModelState.value.copy(
                        initialLocation = LatLng(location.latitude, location.longitude)
                    )
                }
            }
        } catch (e: SecurityException) {
            viewModelState.value = viewModelState.value.copy(errorMessage = "Permiso de ubicación denegado.")
        }
    }

    fun onPermissionResult(isGranted: Boolean) {
        val newStatus = if (isGranted) PermissionStatus.GRANTED else PermissionStatus.DENIED
        viewModelState.value = viewModelState.value.copy(permissionStatus = newStatus)

        if (isGranted) {
            fetchInitialLocation()
        }
    }
}

data class MapUIState(
    val userLocation: LatLng? = null,
    val initialLocation: LatLng? = null,
    val markers: List<MapMarker> = emptyList(),
    val errorMessage: String? = null,
    val permissionStatus: PermissionStatus = PermissionStatus.UNKNOWN
)

enum class PermissionStatus {
    GRANTED, DENIED, UNKNOWN
}