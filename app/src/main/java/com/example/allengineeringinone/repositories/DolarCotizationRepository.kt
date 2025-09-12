package com.example.allengineeringinone.repositories

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.gson.annotations.SerializedName
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

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

    private fun parseDate(date: String): String {
        val dateWithoutTime = date
            .split("T")[0]
            .replace("-", "/")

        val time = date
            .split("T")[1]
            .split(".")[0]


        println("$dateWithoutTime $time")

        return "$dateWithoutTime $time"
    }

    suspend fun getDolarCotization(): DolarCotization {

        var dolarCotization: DolarCotization

        try {
            dolarCotization = retrofit.getDolarCotization()

            dolarCotization = dolarCotization.copy(
                date = parseDate(dolarCotization.date)
            )

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
