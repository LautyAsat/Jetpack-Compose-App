package com.example.allengineeringinone.ui.home.data.model

import com.google.gson.annotations.SerializedName

data class DolarCotization(
    @SerializedName("fechaActualizacion")
    val date: String,

    @SerializedName("compra")
    val buy: Double,

    @SerializedName("venta")
    val sell: Double,
)
