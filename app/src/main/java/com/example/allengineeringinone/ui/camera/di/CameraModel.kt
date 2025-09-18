package com.example.allengineeringinone.ui.camera.di

import android.content.Context
import com.example.allengineeringinone.ui.camera.data.repository.VideoRepository
import com.example.allengineeringinone.ui.camera.data.repository.VideoRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CameraModel {
    @Provides
    @Singleton
    fun provideVideoRepository(
        @ApplicationContext context: Context
    ): VideoRepository {
        return VideoRepositoryImpl(context)
    }
}
