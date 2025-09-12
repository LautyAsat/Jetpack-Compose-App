package com.example.allengineeringinone.ui.call

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun CallEngineeringCousilRoute(){
    val context = LocalContext.current
    val callCousinViewModel: CallEngineeringCousilViewModel = viewModel()

    CallEngineeringCousinButton(
        callEngineeringCousil = { callCousinViewModel.callEngineeringCousil(context) }
    )
}

@Composable
fun CallEngineeringCousinButton(
    callEngineeringCousil: () -> Unit,
){
    Button(
        onClick = {
            callEngineeringCousil()
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
    ) {
        Text(
            text = "Llamar al Consejo",
            textAlign = TextAlign.Center
        )
    }
}