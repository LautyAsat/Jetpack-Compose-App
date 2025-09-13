package com.example.allengineeringinone

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.allengineeringinone.ui.home.HomeRoute
import com.example.allengineeringinone.ui.home.HomeViewModel
import com.example.allengineeringinone.ui.home.battery.BatteryViewModel
import com.example.allengineeringinone.ui.map.MapRoute
import com.example.allengineeringinone.ui.tools.ToolsRoute

@ExperimentalMaterial3Api
@Composable
fun AllEngineerInOneNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    openDrawer: () -> Unit,
    startDestination: String = AllEngineerInOneDestinations.HOME_ROUTE,
){

    // Lo creamos aquí dado que es compartido por todas las rutas
    val batteryViewModel: BatteryViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ){


        composable (
            route = AllEngineerInOneDestinations.HOME_ROUTE
        ){

            val homeViewModel: HomeViewModel = viewModel ()

            HomeRoute(
                batteryViewModel = batteryViewModel,
                homeViewModel = homeViewModel,
                openDrawer = openDrawer
            )
        }

        composable (
            route = AllEngineerInOneDestinations.TOOLS_ROUTE
        ){
            // ToolsScreen

            ToolsRoute(
                batteryViewModel = batteryViewModel,
                openDrawer = openDrawer
            )
        }

        composable (
            route = AllEngineerInOneDestinations.MAPS_ROUTE
        ){
            // MapScreen

            MapRoute(
                batteryViewModel = batteryViewModel,
                openDrawer = openDrawer)
        }

    }

}