package com.example.allengineeringinone.ui.tools.di

import android.content.Context
import com.example.allengineeringinone.ui.camera.data.repository.VideoRepository
import com.example.allengineeringinone.ui.camera.data.repository.VideoRepositoryImpl
import com.example.allengineeringinone.ui.tools.data.repository.AudioRepository
import com.example.allengineeringinone.ui.tools.data.repository.AudioRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ToolsModel {

    @Provides
    @Singleton
    fun provideAudioRepository(
        @ApplicationContext context: Context
    ): AudioRepository {
        return AudioRepositoryImpl(context)
    }
}