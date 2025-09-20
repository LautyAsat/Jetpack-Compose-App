package com.example.allengineeringinone.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.allengineeringinone.ui.common.Battery.BatteryWidget
import com.example.allengineeringinone.ui.home.data.model.HomeEvent
import com.example.allengineeringinone.ui.home.data.model.HomeUIState

/**
 * Renderiza la homeRoute
 *
 * @param openDrawer (evento) solicita abrir el open drawer
 */

@Composable
fun HomeRoute(
    homeViewModel: HomeViewModel = hiltViewModel(),
    openDrawer: () -> Unit
){
    val uiState: HomeUIState by homeViewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        homeViewModel.events.collect { event ->
            when (event) {
                is HomeEvent.LaunchIntent -> {
                    context.startActivity(event.intent)
                }
            }
        }
    }

    HomeScreen(
        uiState = uiState,
        callEngineeringCousil = homeViewModel::callEngineeringCousil,
        batteryWidget = { modifier -> BatteryWidget(modifier) },
        openDrawer = openDrawer
    )
}



