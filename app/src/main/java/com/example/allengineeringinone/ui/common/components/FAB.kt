package com.example.allengineeringinone.ui.common.components

import android.util.Log
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Textsms
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import com.example.allengineeringinone.R

@Composable
fun FAB(
    onClick: () -> Unit
){

    FloatingActionButton(
        onClick = {
            Log.i("FAB_DEBUG", "Abriendo")
            onClick()
        },
        containerColor = colorResource(R.color.black),
        shape = CircleShape
    ) {
        Icon(Icons.Filled.Textsms, "BOTON FAB", tint = colorResource(R.color.white))
    }
}