package com.example.allengineeringinone.ui.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class HomeViewModel() : ViewModel(){

    private val viewModelState = MutableStateFlow(
        HomeViewModelState(
            isLoading = false,
        ),
    )

    val uiState: StateFlow<HomeViewModelState> = viewModelState.asStateFlow()

    init {
        // La lógica de carga inicial de la Home se queda aquí.

        viewModelState.update { it.copy(isLoading = true) }

    }

    fun refreshBattery(){

    }

    fun callEngineeringCousil(){

    }

    fun refreshEngineringFee(){

    }
}


data class HomeViewModelState(
    val isLoading: Boolean = false,
    // El estado del dólar ahora está en su propio ViewModel
)
