package com.example.allengineeringinone.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.allengineeringinone.ui.common.TopAppBar.TopAppBar
import com.example.allengineeringinone.ui.common.components.DolarWidget
import com.example.allengineeringinone.ui.common.components.FAB
import com.example.allengineeringinone.ui.common.components.PrimaryButton
import com.example.allengineeringinone.ui.home.data.model.HomeUIState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    uiState: HomeUIState,
    openDrawer: () -> Unit,
    callEngineeringCousil: () -> Unit
){
    Scaffold(
        topBar = {
            // Barra superior de la app.
            TopAppBar(openDrawer)
        },

        content = { paddingValues ->
            // Contenido principal de la pantalla, debajo de la barra superior.
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {

                DolarWidget(
                    uiState.dolarCotization,
                    Modifier.border(1.dp, Color.Gray, RoundedCornerShape(16.dp))
                )

                Spacer(Modifier.height(20.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                        .background(Color.White, RoundedCornerShape(16.dp))
                        .border(1.dp, Color.Gray, RoundedCornerShape(16.dp))

                )

                Spacer(Modifier.height(20.dp))

                PrimaryButton(
                    Modifier
                        .height(60.dp),
                    callEngineeringCousil,
                    "Llamar al Consejo",
                )

                Spacer(modifier = Modifier.height(20.dp))
            }
        },
        floatingActionButton = { FAB() },
        floatingActionButtonPosition = FabPosition.EndOverlay
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