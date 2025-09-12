package com.example.allengineeringinone.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.allengineeringinone.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    uiState: HomeViewModelState,
    dolarWidget: @Composable (Modifier) -> Unit,
    batteryWidget: @Composable (Modifier) -> Unit,
    callEngineeringCousil: @Composable (Modifier) -> Unit,
    refreshEngineringFee: () -> Unit,
    openDrawer: () -> Unit
){
    Scaffold(
        topBar = {
            // Barra superior de la app.
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
        },
        content = { paddingValues ->
            // Contenido principal de la pantalla, debajo de la barra superior.
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {

                dolarWidget(
                    Modifier
                        .border(1.dp, Color.Gray, RoundedCornerShape(16.dp))
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


                callEngineeringCousil(
                    Modifier
                        .height(60.dp)

                )

                Spacer(modifier = Modifier.height(20.dp))
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