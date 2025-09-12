package com.example.allengineeringinone.repositories

import android.util.Log
import com.google.gson.annotations.SerializedName
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

data class DolarCotization(
    @SerializedName("fechaActualizacion")
    val date: String,

    @SerializedName("compra")
    val buy: Double,

    @SerializedName("venta")
    val sell: Double,
)

interface DolarCotizationService {
    @GET("dolares/oficial")
    suspend fun getDolarCotization(): DolarCotization
}

class DolarCotizationRepository {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://dolarapi.com/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(DolarCotizationService::class.java)

    suspend fun getDolarCotization(): DolarCotization {

        var dolarCotization: DolarCotization

        try {
            dolarCotization = retrofit.getDolarCotization()
        } catch (e: Exception) {
            Log.e("APP_ERROR", "Error obteniendo cotización", e)

            dolarCotization = DolarCotization(
                date = "N/A",
                buy = 0.0,
                sell = 0.0
            )
        }


        return dolarCotization
    }
}
