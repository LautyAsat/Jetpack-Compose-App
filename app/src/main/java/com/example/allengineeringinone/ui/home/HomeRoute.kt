package com.example.allengineeringinone.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.allengineeringinone.ui.home.dolar.DolarRoute
import com.example.allengineeringinone.ui.home.battery.BatteryRoute
import com.example.allengineeringinone.ui.home.battery.BatteryViewModel
import com.example.allengineeringinone.ui.home.call.CallEngineeringCousilRoute

/**
 * Renderiza la homeRoute
 *
 * @param openDrawer (evento) solicita abrir el open drawer
 */
@Composable
fun HomeRoute(
    batteryViewModel: BatteryViewModel,
    homeViewModel: HomeViewModel,
    openDrawer: () -> Unit
){
    val uiState: HomeViewModelState by homeViewModel.uiState.collectAsStateWithLifecycle()

    // TODO: !Crear los Viewmodels aquí.

    HomeScreen(
        uiState = uiState,
        dolarWidget = { modifier -> DolarRoute(modifier) },
        callEngineeringCousil = { modifier -> CallEngineeringCousilRoute(modifier) },
        batteryWidget = { modifier -> BatteryRoute(batteryViewModel, modifier) },
        refreshEngineringFee = homeViewModel::refreshEngineringFee,
        openDrawer = openDrawer
    )
}



