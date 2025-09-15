package com.example.allengineeringinone.ui.common.TopAppBar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    openDrawer: () -> Unit,
    batteryWidget: @Composable (Modifier) -> Unit
){

    TopAppBar(
        title = {
            Text("AllEngineerInOne",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp,0.dp,16.dp,0.dp),
                fontWeight = FontWeight.Bold,)},
        navigationIcon = {
            IconButton (
                onClick = { openDrawer() },
            ) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Menú",
                    modifier = Modifier.size(50.dp)
                )
            }
        },
        actions = {
            batteryWidget(Modifier)
        }
    )
}