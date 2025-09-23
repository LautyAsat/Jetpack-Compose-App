package com.example.allengineeringinone.ui.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.allengineeringinone.R
import com.example.allengineeringinone.ui.home.data.model.HomeEvent
import com.example.allengineeringinone.ui.home.data.model.HomeUIState
import com.example.allengineeringinone.ui.home.data.model.PricesModel
import com.example.allengineeringinone.ui.home.data.repository.DolarCotizationRepository
import com.example.allengineeringinone.ui.home.data.repository.PricesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dolarCotizationRepository: DolarCotizationRepository,
    private val pricesRepository: PricesRepository
) : ViewModel(){

    private val viewModelState = MutableStateFlow(
        HomeUIState(),
    )
    val uiState: StateFlow<HomeUIState> = viewModelState.asStateFlow()

    private val _events = Channel<HomeEvent>()
    val events = _events.receiveAsFlow()


    init {
        viewModelState.update { it.copy(isDolarLoading = true, isPricesLoading = true) }
        refreshDolar()

        loadPrices()
    }

    fun refreshDolar(){
        viewModelState.update { it.copy(isDolarLoading = true) }

        viewModelScope.launch {
            val dolarData = dolarCotizationRepository.getDolarCotization()
            viewModelState.update {
                it.copy(
                    isDolarLoading = false,
                    dolarCotization = dolarData
                )
            }
        }
    }

    fun callEngineeringCousil(){
        viewModelScope.launch {
            val enginerringCousilPhoneNumber = context.getString(R.string.engineering_cousil_phone_number)

            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = "tel:$enginerringCousilPhoneNumber".toUri()
            }

            _events.send(HomeEvent.LaunchIntent(intent))
        }
    }

    fun loadPrices(){
        viewModelScope.launch {
            viewModelState.update { it.copy(isPricesLoading = true) }

            pricesRepository.getPrices()
                .onSuccess { prices: PricesModel ->
                    viewModelState.update {
                        it.copy(
                            isPricesLoading = false,
                            prices = prices
                        )
                    }
                }.onFailure { error ->
                    viewModelState.update {
                        it.copy(
                            isPricesLoading = false,
                            prices = null
                        )
                    }
                }
        }
    }

}
