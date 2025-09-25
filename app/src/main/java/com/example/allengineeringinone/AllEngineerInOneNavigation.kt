package com.example.allengineeringinone

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination

/*
* Rutas destino de la app 'AllEngineerInOne'
* */
object AllEngineerInOneDestinations {
    const val HOME_ROUTE = "home"
    const val TOOLS_ROUTE = "tools"
    const val MAPS_ROUTE = "maps"
    const val CAMERA_ROUTE = "camera"
    const val AR_ROUTE = "ar"
}


/*
* Acciones de navegación de la app 'AllEngineerInOne'
* */
class AllEngineerInOneNavigation(navController: NavController) {

    val navigationToHome: () -> Unit = {
        navController.navigate(AllEngineerInOneDestinations.HOME_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    val navigationToTools: () -> Unit = {
        navController.navigate(AllEngineerInOneDestinations.TOOLS_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    val navigationToMap: () -> Unit = {
        navController.navigate(AllEngineerInOneDestinations.MAPS_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    val navigationToCamera: () -> Unit =  {
        navController.navigate(AllEngineerInOneDestinations.CAMERA_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    val navigationToAR: () -> Unit =  {
        navController.navigate(AllEngineerInOneDestinations.AR_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}