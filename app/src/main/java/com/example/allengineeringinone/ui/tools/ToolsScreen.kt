package com.example.allengineeringinone.ui.tools

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.allengineeringinone.ui.common.TopAppBar.TopAppBar


@Composable
fun ToolsScreen(
    openDrawer: () -> Unit,
    batteryWidget: @Composable (Modifier) -> Unit
){
    Scaffold(
        topBar = {
            // Barra superior de la app.
            TopAppBar(openDrawer, batteryWidget)
        },

        content = { paddingValues ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                Text("Tools")
            }
        }
    )
}