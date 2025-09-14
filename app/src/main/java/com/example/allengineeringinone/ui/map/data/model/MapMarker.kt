package com.example.allengineeringinone.ui.map.data.model

import com.google.android.gms.maps.model.LatLng
import com.google.firebase.Timestamp
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ServerTimestamp

data class MapMarker(
    val name: String = "",
    val location: GeoPoint? = null,

    @ServerTimestamp
    val date: Timestamp? = null,
)
