package com.example.allengineeringinone.ui.map.data.model

import com.google.android.gms.maps.model.LatLng

data class MapUIState(
    val userLocation: LatLng? = null,
    val initialLocation: LatLng? = null,
    val markers: List<MapMarker> = emptyList(),
    val errorMessage: String? = null,
    val permissionStatus: PermissionStatus = PermissionStatus.UNKNOWN
)