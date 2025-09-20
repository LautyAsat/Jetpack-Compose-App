package com.example.allengineeringinone.ui.home.data.model

data class HomeUIState(
    val isDolarLoading: Boolean = false,
    val isPricesLoading: Boolean = false,
    val dolarCotization: DolarCotization? = null,
)