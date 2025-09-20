package com.example.allengineeringinone.ui.home.data.repository

import com.example.allengineeringinone.ui.home.data.model.DolarCotization


interface DolarCotizationRepository {
    suspend fun getDolarCotization(): DolarCotization
}