package com.example.allengineeringinone

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.allengineeringinone.ui.ar.ArRoute
import com.example.allengineeringinone.ui.camera.CameraRoute
import com.example.allengineeringinone.ui.home.HomeRoute
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

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ){


        composable (
            route = AllEngineerInOneDestinations.HOME_ROUTE
        ){
            HomeRoute(
                openDrawer = openDrawer
            )
        }

        composable (
            route = AllEngineerInOneDestinations.TOOLS_ROUTE
        ){
            // ToolsScreen
            ToolsRoute(
                openDrawer = openDrawer
            )
        }

        composable (
            route = AllEngineerInOneDestinations.MAPS_ROUTE
        ){
            // MapScreen

            MapRoute(
                openDrawer = openDrawer)
        }

        composable (
            route = AllEngineerInOneDestinations.CAMERA_ROUTE
        ){
            // MapScreen

            CameraRoute()
        }

        composable (
            route = AllEngineerInOneDestinations.AR_ROUTE
        ){
            ArRoute()
        }
    }

}