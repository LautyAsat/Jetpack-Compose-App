package com.example.allengineeringinone.ui.tools.di

import com.example.allengineeringinone.ui.tools.data.repository.ToolsRepository
import com.example.allengineeringinone.ui.tools.data.repository.ToolsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ToolsModel {

    @Provides
    @Singleton
    fun provideToolsRepository(): ToolsRepository {
        return ToolsRepositoryImpl()
    }
}