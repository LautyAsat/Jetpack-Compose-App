package com.example.allengineeringinone.ui.home.dolar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.allengineeringinone.repositories.DolarCotization
import com.example.allengineeringinone.repositories.DolarCotizationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DolarViewModel(
    private val dolarCotizationRepository: DolarCotizationRepository = DolarCotizationRepository()
) : ViewModel() {

    private val viewModelState = MutableStateFlow(
        DolarViewModelState(
            isLoading = false,
            dolarCotization = null,
        ),
    )

    val uiState: StateFlow<DolarViewModelState> = viewModelState.asStateFlow()

    init {
        refreshDolar()
    }

    fun refreshDolar() {
        viewModelState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            val dolarData = dolarCotizationRepository.getDolarCotization()
            viewModelState.update {
                it.copy(
                    isLoading = false,
                    dolarCotization = dolarData
                )
            }
        }
    }
}

// El estado de la UI para el widget del dólar
data class DolarViewModelState(
    val isLoading: Boolean,
    val dolarCotization: DolarCotization?
)
