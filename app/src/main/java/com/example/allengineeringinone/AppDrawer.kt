package com.example.allengineeringinone

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun AppDrawer(
    drawerState: DrawerState,
    currentRoute: String,
    modifier: Modifier = Modifier,
    closeDrawer: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToTools: () -> Unit,
){
    ModalDrawerSheet (
        drawerState = drawerState,
        modifier = modifier
    ){

        Spacer(Modifier.padding(4.dp))

        AllEngineerInOneLogo()

        HorizontalDivider(thickness = 2.dp)

        NavigationDrawerItem(
            label = { Text(text = "Home") },
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            selected = currentRoute == AllEngineerInOneDestinations.HOME_ROUTE,
            onClick = {
                navigateToHome()
                closeDrawer()
            }
        )
        // Agrega más elementos de menú aquí.
        NavigationDrawerItem(
            label = { Text(text = "Herramientas") },
            selected = currentRoute == AllEngineerInOneDestinations.TOOLS_ROUTE,
            onClick = {
                navigateToTools()
                closeDrawer()
            }
        )
    }
}

@Composable
fun AllEngineerInOneLogo(modifier: Modifier = Modifier){
    Row(modifier = modifier) {
        Icon(
            painterResource(R.drawable.ic_allengineerinone_logo),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
        )
        Spacer(Modifier.width(8.dp))
        Text("AllEngineeringInOne", fontFamily = FontFamily.Cursive)
    }
}