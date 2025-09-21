package com.example.allengineeringinone

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllEngineerInOneApp(){

    val navController = rememberNavController()

    // Remember recibe la key del navController y lo guarda en forma de estado, en caso de cambiar navController, este tambien cambia nuevamente.
    val navigationActions = remember (navController) {
        AllEngineerInOneNavigation(navController)
    }

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val courutineScope = rememberCoroutineScope() // nos permite lanzar corrutinas cortas si bloquear el hilo principal de la UI.


    val navBackStackEntry by navController.currentBackStackEntryAsState() // convertimos el backStack en estado, es decir, un valor observable.
    val currentRoute = navBackStackEntry?.destination?.route ?: AllEngineerInOneDestinations.HOME_ROUTE


    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawer(
                drawerState = drawerState,
                currentRoute = currentRoute,
                closeDrawer = { courutineScope.launch{ closeDrawer(drawerState)} },
                navigateToTools = navigationActions.navigationToTools,
                navigateToHome = navigationActions.navigationToHome,
                navigateToMap = navigationActions.navigationToMap,
                navigateToCamera = navigationActions.navigationToCamera,
                navigateToAR = navigationActions.navigationToAR
            )
        },
        gesturesEnabled = false,
    ) {
        AllEngineerInOneNavGraph(
            navController = navController,
            openDrawer = { courutineScope.launch{ openDrawer(drawerState)} }
        )
    }

}

/**
 * Cerrar el Drawer
 */
suspend fun closeDrawer(drawerState: DrawerState){
    drawerState.apply {
        close()
    }
}

/**
 * Abrir el Drawer
 */
suspend fun openDrawer(drawerState: DrawerState){
    drawerState.apply {
        open()
    }
}