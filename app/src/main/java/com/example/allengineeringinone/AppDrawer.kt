package com.example.allengineeringinone

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


/*
* Este archivo contiene la implementación del cajón de navegación lateral de la aplicación (AppDrawer).
* Define la estructura y el comportamiento que tendra cada uno de los botones del panel.
* */
@Composable
fun AppDrawer(
    drawerState: DrawerState,
    currentRoute: String,
    modifier: Modifier = Modifier,
    closeDrawer: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToTools: () -> Unit,
    navigateToMap: () -> Unit,
    navigateToCamera: () -> Unit,
){
    ModalDrawerSheet (
        drawerState = drawerState,
        modifier = modifier
    ){

        Spacer(Modifier.padding(4.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ){

            IconButton (
                onClick = { closeDrawer() },
            ) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Menú",
                    modifier = Modifier.size(50.dp).padding(4.dp, 0.dp, 0.dp, 0.dp)
                )
            }

            AllEngineerInOneLogo(modifier = Modifier.padding(16.dp, 0.dp))
        }

        Spacer(Modifier.padding(4.dp))

        HorizontalDivider(thickness = 2.dp)

        Spacer(Modifier.padding(8.dp))

        NavigationDrawerItem(
            label = { Text(text = "Home") },
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            selected = currentRoute == AllEngineerInOneDestinations.HOME_ROUTE,
            shape = RectangleShape,
            onClick = {
                navigateToHome()
                closeDrawer()
            }
        )

        NavigationDrawerItem(
            label = { Text(text = "Herramientas") },
            icon = { Icon(Icons.Filled.Build, contentDescription = "Tools") },
            selected = currentRoute == AllEngineerInOneDestinations.TOOLS_ROUTE,
            shape = RectangleShape,
            onClick = {
                navigateToTools()
                closeDrawer()
            }
        )

        NavigationDrawerItem(
            label = { Text(text = "Mapa") },
            icon = { Icon(Icons.Filled.Map, contentDescription = "Map") },
            selected = currentRoute == AllEngineerInOneDestinations.MAPS_ROUTE,
            shape = RectangleShape,
            onClick = {
                navigateToMap()
                closeDrawer()
            }
        )

        NavigationDrawerItem(
            label = { Text(text = "Camera") },
            icon = { Icon(Icons.Filled.CameraAlt, contentDescription = "Camera") },
            selected = currentRoute == AllEngineerInOneDestinations.MAPS_ROUTE,
            shape = RectangleShape,
            onClick = {
                navigateToCamera()
                closeDrawer()
            }
        )
    }
}

@Composable
fun AllEngineerInOneLogo(modifier: Modifier = Modifier){
    Row(modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(Modifier.width(8.dp))
        Text(
            text = "AllEngineeringInOne",
            fontWeight = FontWeight.Black,
            fontSize = 22.sp
        )
    }
}