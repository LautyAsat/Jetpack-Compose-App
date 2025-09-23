package com.example.allengineeringinone.ui.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.allengineeringinone.R
import com.example.allengineeringinone.ui.home.data.model.PricesModel

@Composable
fun FeePrices(
    prices: PricesModel?
){

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

        PriceRow(label = "Precio por hora ingeniero", price = "$${prices?.priceEngineerPerHour}")
        PriceRow(label = "Precio por viga", price = "$${prices?.beamPrice}")
        PriceRow(label = "Precio Kg de arena", price = "$${prices?.sandPricePerKilogram}")
        PriceRow(label = "Precio Kg de porlán", price = "$${prices?.porlanPricePerKilogram}")
        PriceRow(label = "Precio por unidad de ladrillo", price = "$${prices?.bricksPrice}")
    }


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