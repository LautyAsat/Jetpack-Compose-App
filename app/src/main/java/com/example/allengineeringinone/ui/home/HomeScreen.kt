package com.example.allengineeringinone.ui.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.allengineeringinone.R
import com.example.allengineeringinone.ui.common.Chat.ChatWidget
import com.example.allengineeringinone.ui.common.Chat.data.model.ChatActions
import com.example.allengineeringinone.ui.common.Chat.data.model.ChatUIState
import com.example.allengineeringinone.ui.common.Chat.data.model.LocalChatActions
import com.example.allengineeringinone.ui.common.TopAppBar.TopAppBar
import com.example.allengineeringinone.ui.common.components.DolarWidget
import com.example.allengineeringinone.ui.common.components.FAB
import com.example.allengineeringinone.ui.common.components.FeePrices
import com.example.allengineeringinone.ui.common.components.PrimaryButton
import com.example.allengineeringinone.ui.common.components.SkeletonLoader
import com.example.allengineeringinone.ui.home.data.model.HomeUIState



/**
 * Información: HomeScreen es la vista stateless de la pantalla de inicio.
 *
 * @param uiState: estado de la pantalla
 * @param openDrawer: abre el drawer
 * @param callEngineeringCousil: llama al consejo
 * @param chatUIState: estado del chat
 * */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    uiState: HomeUIState,
    openDrawer: () -> Unit,
    callEngineeringCousil: () -> Unit,
    chatUIState: ChatUIState,
    chatActions: ChatActions
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
                    .verticalScroll(rememberScrollState())
            ) {

                if(!uiState.isDolarLoading){
                    DolarWidget(
                        uiState.dolarCotization,
                        Modifier.border(1.dp, Color.Gray, RoundedCornerShape(16.dp))
                    )
                }
                else{
                    SkeletonLoader(modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .size(80.dp)
                            .border(1.dp, Color.Gray, RoundedCornerShape(16.dp))
                    )
                }

                Spacer(Modifier.height(20.dp))

                if(!uiState.isPricesLoading){
                    FeePrices(uiState.prices)
                }
                else{
                    SkeletonLoader(modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .size(80.dp)
                            .border(1.dp, Color.Gray, RoundedCornerShape(16.dp))
                    )
                }

                Spacer(Modifier.height(20.dp))

                PrimaryButton(
                    Modifier
                        .height(60.dp),
                    callEngineeringCousil,
                    "Llamar al Consejo",
                )

                Spacer(modifier = Modifier.height(20.dp))

                ChatWidget(
                    uiState = chatUIState,
                    paddingValues = paddingValues,
                    onToggleChat = chatActions.onToggleChat,
                    onSendMessage = chatActions.onMessageChatSent,
                    onTextFieldChanged = chatActions.onTextFieldChanged
                )
            }
        },
        floatingActionButton = { FAB { chatActions.onToggleChat() } },
        floatingActionButtonPosition = FabPosition.EndOverlay
    )
}

