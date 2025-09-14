package com.example.allengineeringinone.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.allengineeringinone.ui.map.data.model.MapMarker
import com.example.allengineeringinone.ui.map.data.repository.MapRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
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
                    viewModelState.value = MapUIState(markers = markers)
                }.onFailure { error ->
                    viewModelState.value = MapUIState(errorMessage = error.message)
                }
            }
        }
    }

    fun addMarker(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            repository.saveMarker(latitude, longitude).onFailure { error ->
                viewModelState.value = viewModelState.value.copy(errorMessage = error.message)
            }
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
}

data class MapUIState(
    val userLocation: LatLng? = null,
    val initialLocation: LatLng? = null,
    val markers: List<MapMarker> = emptyList(),
    val errorMessage: String? = null
)