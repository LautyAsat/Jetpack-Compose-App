package com.example.allengineeringinone.ui.call

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.allengineeringinone.R

@Composable
fun CallEngineeringCousilRoute(modifier: Modifier){
    val context = LocalContext.current
    val callCousinViewModel: CallEngineeringCousilViewModel = viewModel()

    CallEngineeringCousinButton(
        callEngineeringCousil = { callCousinViewModel.callEngineeringCousil(context) },
        modifier = modifier
    )
}

@Composable
fun CallEngineeringCousinButton(
    callEngineeringCousil: () -> Unit,
    modifier: Modifier
){
    Button(
        onClick = {
            callEngineeringCousil()
        },
        modifier = modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.primary),      // Fondo del botón
            contentColor  = Color.White        // Color del texto e iconos
        ),
        shape = RoundedCornerShape(16.dp)
        ) {
        Text(
            text = "Llamar al Consejo",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Black,
            fontSize = 18.sp
        )
    }
}