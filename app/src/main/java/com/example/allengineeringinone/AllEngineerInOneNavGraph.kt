package com.example.allengineeringinone

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.allengineeringinone.ui.home.HomeRoute
import com.example.allengineeringinone.ui.home.HomeViewModel
import com.example.allengineeringinone.ui.map.MapRoute
import com.example.allengineeringinone.ui.tools.ToolsRoute

@Composable
fun AllEngineerInOneNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    openDrawer: () -> Unit,
    startDestination: String = AllEngineerInOneDestinations.HOME_ROUTE,
){
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
                homeViewModel,
                openDrawer
            )
        }

        composable (
            route = AllEngineerInOneDestinations.TOOLS_ROUTE
        ){
            // ToolsScreen

            ToolsRoute()
        }

        composable (
            route = AllEngineerInOneDestinations.MAPS_ROUTE
        ){
            // MapScreen

            MapRoute()
        }

    }

}