package com.example.allengineeringinone.ui.map.data.repository

import com.example.allengineeringinone.ui.map.data.model.MapMarker
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow

interface MapRepository {
    fun getMarkers(): Flow<Result<List<MapMarker>>>

    suspend fun saveMarker(latitude: Double, longitude: Double): Result<Unit>
}