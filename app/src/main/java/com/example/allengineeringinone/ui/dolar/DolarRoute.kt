package com.example.allengineeringinone.ui.dolar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel


// Este es el punto de entrada para el widget del dólar.
// Se encarga de obtener el ViewModel y pasar el estado a la vista.
@Composable
fun DolarRoute(
    // No necesita parámetros, ya que crea su propio ViewModel
) {
    val dolarViewModel: DolarViewModel = viewModel()
    val uiState: DolarViewModelState by dolarViewModel.uiState.collectAsStateWithLifecycle()

    DolarWidget(
        uiState = uiState,
        refreshDolar = dolarViewModel::refreshDolar
    )
}

// Este es el composable "tonto" que solo dibuja la UI.
// No tiene idea de ViewModels, repositorios, etc.

@Composable
fun DolarWidget(
    uiState: DolarViewModelState,
    refreshDolar: () -> Unit
){

    Box(
        modifier = Modifier
            .background(Color.White, RoundedCornerShape(4.dp))
            .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
            .padding(4.dp, 2.dp)
            .height(400.dp)
    ){
        Column {
            Text(
                text = "Dolar Oficial",
                modifier = Modifier
                    .fillMaxWidth()
                ,
                color = Color.Green,
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Row {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Compra",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "$ ${uiState.dolarCotization?.buy}",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }

                Column(modifier = Modifier.weight(1f)){
                    Text(
                        text = "Venta",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "$ ${uiState.dolarCotization?.sell}",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
