package com.example.allengineeringinone.ui.map.data.repository

import android.util.Log
import com.example.allengineeringinone.ui.map.data.model.MapMarker
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.sql.Timestamp
import android.os.Build
class MapRepositoryImpl(
    private val firebaseStore: FirebaseFirestore
): MapRepository{

    override fun getMarkers(): Flow<Result<List<MapMarker>>> = callbackFlow {
        val collection = firebaseStore.collection("markers")

        Log.i("MapRepository", "Collection: $collection")

        val snapshopListener = collection.addSnapshotListener { snapshot, error ->

            if(error != null){
                trySend(Result.failure(error))
                Log.e("MapRepository", "Error al obtener los marcadores", error)
                return@addSnapshotListener
            }

            if (snapshot != null) {
                val markers = snapshot.toObjects<MapMarker>()
                Log.i("MapRepository", "markers: $markers")
                trySend(Result.success(markers))
            }
        }

        awaitClose { snapshopListener.remove() }
    }

    override suspend fun saveMarker(latitude: Double, longitude: Double): Result<Unit> {
        return try {
            val newMarker = MapMarker(
                location = GeoPoint(latitude, longitude),
                name = Build.MODEL
            )

            // Firestore asignará un ID automáticamente
            Log.i("map", "Se guarod bine")
            firebaseStore.collection("markers").add(newMarker).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("map", "Error al guardar el marcador", e)
            Result.failure(e)
        }
    }
}