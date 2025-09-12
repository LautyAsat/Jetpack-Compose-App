package com.example.allengineeringinone.repositories

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.ui.res.stringResource
import com.example.allengineeringinone.R


class CallEngineeringCousilRepository(){

    fun callEngineeringCousil(context: Context){

        val enginerringCousilPhoneNumber = context.getString(R.string.engineering_cousil_phone_number)

        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$enginerringCousilPhoneNumber")
        }

        context.startActivity(intent)
    }
}