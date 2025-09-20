package com.example.allengineeringinone.ui.home.data.model

import android.content.Intent

sealed class HomeEvent {
    data class LaunchIntent(val intent: Intent) : HomeEvent()
}