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

    HomeScreen(
        uiState = uiState,
        dolarWidget = { modifier -> DolarRoute(modifier) },
        callEngineeringCousil = { modifier -> CallEngineeringCousilRoute(modifier) },
        refreshBattery = homeViewModel::refreshBattery,
        refreshEngineringFee = homeViewModel::refreshEngineringFee,
        openDrawer = openDrawer
    )
}



