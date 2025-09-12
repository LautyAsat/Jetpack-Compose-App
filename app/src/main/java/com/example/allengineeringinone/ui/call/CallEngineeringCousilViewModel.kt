package com.example.allengineeringinone.ui.call

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.allengineeringinone.repositories.CallEngineeringCousilRepository

public class CallEngineeringCousilViewModel(
    private val callCousinRepository: CallEngineeringCousilRepository  = CallEngineeringCousilRepository()
) : ViewModel() {

    fun callEngineeringCousil(context: Context){
        callCousinRepository.callEngineeringCousil(context)
    }
}