package com.example.allengineeringinone.ui.home.data.repository

import android.util.Log
import com.example.allengineeringinone.ui.home.data.model.DolarCotization
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import javax.inject.Singleton

interface DolarCotizationService {
    @GET("dolares/oficial")
    suspend fun getDolarCotization(): DolarCotization
}


@Singleton
class DolarCotizationRepositoryImpl : DolarCotizationRepository {
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


        return "$dateWithoutTime $time"
    }

    override suspend fun getDolarCotization(): DolarCotization {

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

