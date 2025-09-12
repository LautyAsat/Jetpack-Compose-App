package com.example.allengineeringinone.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.allengineeringinone.repositories.DolarCotization
import com.example.allengineeringinone.ui.dolar.DolarWidget

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    uiState: HomeViewModelState,
    dolarWidget: @Composable () -> Unit,
    refreshBattery: () -> Unit,
    callEngineeringCousil: @Composable () -> Unit,
    refreshEngineringFee: () -> Unit,
    openDrawer: () -> Unit
){
    Scaffold(
        topBar = {
            // Barra superior de la app.
            TopAppBar(
                title = { Text("Herramientas para Ingenieros") },
                navigationIcon = {
                    IconButton (onClick = {
                        openDrawer()
                    }) {
                        Icon(Icons.Filled.Menu, contentDescription = "Menú")
                    }
                }
            )
        },
        content = { paddingValues ->
            // Contenido principal de la pantalla, debajo de la barra superior.
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                dolarWidget()

                callEngineeringCousil()

                Spacer(modifier = Modifier.height(20.dp))
                Text(text = "Aquí podrás agregar el widget de cotización del dólar y otras funciones.")
            }
        }
    )
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LoadingContent(
    empty: Boolean,
    emptyContent: @Composable () -> Unit,
    loading: Boolean,
    onRefresh: () -> Unit,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (empty) {
        emptyContent()
    } else {
        val refreshState = rememberPullToRefreshState()


        PullToRefreshBox(
            isRefreshing = loading,
            onRefresh = onRefresh,
            content = { content() },
            state = refreshState,
            indicator = {
                Indicator(
                    modifier = modifier
                        .align(Alignment.TopCenter)
                        .padding(),
                    isRefreshing = loading,
                    state = refreshState,
                )
            },
        )
    }
}