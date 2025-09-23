package com.example.allengineeringinone.ui.home.data.repository

import android.util.Log
import com.example.allengineeringinone.ui.home.data.model.PricesModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PricesRepositoryImpl @Inject constructor(
    private val firebaseStore: FirebaseFirestore
): PricesRepository {

    override suspend fun getPrices(): Result<PricesModel> {
        return try {
            val result = firebaseStore.collection("fee_prices").get().await()

            val document = result.documents.firstOrNull()

            if (document != null) {
                val pricesModel = document.toObject(PricesModel::class.java)

                if (pricesModel != null) {
                    Log.d("PRICES_DEBUG", "Precios: $pricesModel")
                    Result.success(pricesModel)
                } else {
                    Result.failure(Exception("No se pudo convertir a PricesModel"))
                }
            } else {
                Result.failure(Exception("No se encontró ningún documento en la colección 'fee_prices'"))
            }
        } catch (e: Exception) {
            Log.e("PRICES_DEBUG", "Error", e)
            Result.failure(e)
        }
    }
}
