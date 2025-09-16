package com.example.allengineeringinone.ui.tools.data.repository

interface ToolsRepository {
    suspend fun turnOnLighter(): Result<Unit>
    suspend fun turnOffLighter(): Result<Unit>
    suspend fun turnOnRuler(): Result<Unit>
    suspend fun turnOffRuler(): Result<Unit>
}