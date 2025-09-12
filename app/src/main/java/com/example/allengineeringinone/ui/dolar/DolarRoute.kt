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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.allengineeringinone.R


// Este es el punto de entrada para el widget del dólar.
// Se encarga de obtener el ViewModel y pasar el estado a la vista.
@Composable
fun DolarRoute(
    modifier: Modifier = Modifier
    // No necesita parámetros, ya que crea su propio ViewModel
) {
    val dolarViewModel: DolarViewModel = viewModel()
    val uiState: DolarViewModelState by dolarViewModel.uiState.collectAsStateWithLifecycle()

    DolarWidget(
        uiState = uiState,
        refreshDolar = dolarViewModel::refreshDolar,
        modifier = modifier
    )
}

// Este es el composable "tonto" que solo dibuja la UI.
// No tiene idea de ViewModels, repositorios, etc.

@Composable
fun DolarWidget(
    uiState: DolarViewModelState,
    refreshDolar: () -> Unit,
    modifier: Modifier = Modifier
){

    Box(
        modifier = modifier
            .background(Color.White, RoundedCornerShape(4.dp))
            .padding(16.dp)
    ){
        Column {
            Text(
                text = "Dolar Oficial",
                modifier = Modifier
                    .fillMaxWidth()
                ,
                color = colorResource(id = R.color.dark_gray),
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Row (modifier = Modifier.padding(0.dp,16.dp,0.dp,0.dp)){
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Compra",
                        modifier = Modifier.fillMaxWidth(),
                        color = colorResource(id = R.color.gray),
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Text(
                        text = "$ ${uiState.dolarCotization?.buy}",
                        modifier = Modifier.fillMaxWidth().padding(0.dp,20.dp,0.dp,8.dp),
                        textAlign = TextAlign.Center,
                        color = colorResource(id = R.color.dolar_green),
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Black
                    )
                }

                Column(modifier = Modifier.weight(1f)){
                    Text(
                        text = "Venta",
                        modifier = Modifier.fillMaxWidth(),
                        color = colorResource(id = R.color.gray),
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Text(
                        text = "$ ${uiState.dolarCotization?.sell}",
                        modifier = Modifier.fillMaxWidth().padding(0.dp,20.dp,0.dp,8.dp),
                        textAlign = TextAlign.Center,
                        color = colorResource(id = R.color.dolar_green),
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Black
                    )
                }
            }

            Text(
                "Actualizado por última vez: ${uiState.dolarCotization?.date}",
                modifier = Modifier.fillMaxWidth().padding(0.dp,0.dp,16.dp, 0.dp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Light)
        }
    }
}
