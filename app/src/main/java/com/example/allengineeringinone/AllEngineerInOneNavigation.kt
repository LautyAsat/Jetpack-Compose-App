package com.example.allengineeringinone

import androidx.navigation.NavController

/*
* Rutas destino de la app 'AllEngineerInOne'
* */

object AllEngineerInOneDestinations {
    const val HOME_ROUTE = "home"
    const val TOOLS_ROUTE = "tools"
    const val MAPS_ROUTE = "maps"
}


/*
* Acciones de navegación de la app 'AllEngineerInOne'
* */


class AllEngineerInOneNavigation(navController: NavController) {

    val navigationToHome: () -> Unit = {
        // Navegamos
    }

    val navigationToTools: () -> Unit = {

    }

    val navigationToMaps: () -> Unit = {

    }

}