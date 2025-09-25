package com.example.allengineeringinone

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.allengineeringinone.ui.ar.ArRoute
import com.example.allengineeringinone.ui.camera.CameraRoute
import com.example.allengineeringinone.ui.common.Chat.ChatViewModel
import com.example.allengineeringinone.ui.common.Chat.data.model.ChatActions
import com.example.allengineeringinone.ui.common.Chat.data.model.LocalChatActions
import com.example.allengineeringinone.ui.home.HomeRoute
import com.example.allengineeringinone.ui.map.MapRoute
import com.example.allengineeringinone.ui.tools.ToolsRoute

@ExperimentalMaterial3Api
@Composable
fun AllEngineerInOneNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    openDrawer: () -> Unit,
    startDestination: String = AllEngineerInOneDestinations.HOME_ROUTE,
){

    val chatViewModel: ChatViewModel = hiltViewModel()
    val chatUIState by chatViewModel.uiState.collectAsStateWithLifecycle()
    val chatActions = ChatActions (
        onToggleChat = chatViewModel::toggleChat,
        onMessageChatSent = chatViewModel::onSendMessage,
        onTextFieldChanged = chatViewModel::onTextFieldChanged
    )

    CompositionLocalProvider (LocalChatActions provides chatActions) {
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = modifier
        ){


            composable (
                route = AllEngineerInOneDestinations.HOME_ROUTE
            ){
                HomeRoute(
                    openDrawer = openDrawer,
                    chatUIState = chatUIState
                )
            }

            composable (
                route = AllEngineerInOneDestinations.TOOLS_ROUTE
            ){
                // ToolsScreen
                ToolsRoute(
                    openDrawer = openDrawer,
                    chatUIState = chatUIState
                )
            }

            composable (
                route = AllEngineerInOneDestinations.MAPS_ROUTE
            ){
                // MapScreen

                MapRoute(
                    openDrawer = openDrawer,
                    chatUIState = chatUIState
                )
            }

            composable (
                route = AllEngineerInOneDestinations.CAMERA_ROUTE
            ){
                // MapScreen

                CameraRoute()
            }

            composable (
                route = AllEngineerInOneDestinations.AR_ROUTE
            ){
                ArRoute()
            }
        }
    }

}