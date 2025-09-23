package com.example.allengineeringinone.ui.home.data.repository

import com.example.allengineeringinone.ui.home.data.model.PricesModel
import kotlinx.coroutines.flow.Flow

interface PricesRepository {
    suspend fun getPrices(): Result<PricesModel>
}