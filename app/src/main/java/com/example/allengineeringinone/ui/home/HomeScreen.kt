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
import com.example.allengineeringinone.ui.common.TopAppBar.TopAppBar
import com.example.allengineeringinone.ui.common.components.DolarWidget
import com.example.allengineeringinone.ui.common.components.FAB
import com.example.allengineeringinone.ui.common.components.PrimaryButton
import com.example.allengineeringinone.ui.common.components.SkeletonLoader
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

                    Column (
                        Modifier.fillMaxWidth()
                            .background(Color.White)
                            .border(1.dp, Color.Gray, RoundedCornerShape(16.dp))
                            .padding(16.dp)
                    ){

                        Text(
                            text = "Precios",
                            modifier = Modifier
                                .fillMaxWidth()
                            ,
                            color = colorResource(id = R.color.dark_gray),
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(Modifier.height(16.dp))

                        PriceRow(label = "Precio por hora ingeniero", price = "$${uiState.prices?.priceEngineerPerHour}")
                        PriceRow(label = "Precio por viga", price = "$${uiState.prices?.beamPrice}")
                        PriceRow(label = "Precio Kg de arena", price = "$${uiState.prices?.sandPricePerKilogram}")
                        PriceRow(label = "Precio Kg de porlán", price = "$${uiState.prices?.porlanPricePerKilogram}")
                        PriceRow(label = "Precio por unidad de ladrillo", price = "$${uiState.prices?.bricksPrice}")
                    }


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
            }
        },
        floatingActionButton = { FAB() },
        floatingActionButtonPosition = FabPosition.EndOverlay
    )
}


@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float,
    important: Boolean = false
) {
    Text(
        text = text,
        modifier = Modifier
            .weight(weight)
            .padding(12.dp)
        ,
        fontWeight = if (important) FontWeight.Medium else FontWeight.Normal,
        textAlign = if (important) TextAlign.Center else TextAlign.Start
    )
}


/**
 * Un Composable que renderiza una fila completa para la tabla de precios.
 * @param label El texto descriptivo del precio (ej: "Precio por viga").
 * @param price El valor del precio a mostrar.
 */
@Composable
fun PriceRow(label: String, price: String) {
    Row(
        Modifier
            .fillMaxWidth()
            .background(Color.White)
            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
    ) {
        TableCell(text = label, weight = 0.6f)
        TableCell(text = price, weight = 0.4f, important = true)
    }
    Spacer(Modifier.height(8.dp))
}