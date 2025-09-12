package com.example.allengineeringinone.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.allengineeringinone.ui.dolar.DolarRoute
import com.example.allengineeringinone.openDrawer
import com.example.allengineeringinone.ui.call.CallEngineeringCousilRoute

/**
 * Renderiza la homeRoute
 *
 * @param openDrawer (evento) solicita abrir el open drawer
 */
@Composable
fun HomeRoute(
    homeViewModel: HomeViewModel,
    openDrawer: () -> Unit
){
    val uiState: HomeViewModelState by homeViewModel.uiState.collectAsStateWithLifecycle()

    HomeRoute(
        uiState = uiState,
        refreshBattery = homeViewModel::refreshBattery,
        refreshEngineringFee = homeViewModel::refreshEngineringFee,
        openDrawer = openDrawer,
        dolarWidget = { DolarRoute() },
        callEngineeringCousil = { CallEngineeringCousilRoute() }
    )
}




@Composable
fun HomeRoute(
    uiState: HomeViewModelState,
    refreshBattery: () -> Unit,
    callEngineeringCousil: @Composable () -> Unit,
    refreshEngineringFee: () -> Unit,
    openDrawer: () -> Unit,
    dolarWidget: @Composable () -> Unit // Recibe el widget como un Composable
){
    //TODO: AGREGAR VARIANTES DEPENDIENDO EL STATE DEL HOME SCREEN.

    HomeScreen(
        uiState = uiState,
        dolarWidget = dolarWidget,
        refreshBattery = refreshBattery,
        callEngineeringCousil = callEngineeringCousil,
        refreshEngineringFee = refreshEngineringFee,
        openDrawer = openDrawer
    )
}
