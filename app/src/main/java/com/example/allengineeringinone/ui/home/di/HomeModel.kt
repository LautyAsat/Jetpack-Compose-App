package com.example.allengineeringinone.ui.home.di

import com.example.allengineeringinone.ui.home.data.repository.DolarCotizationRepository
import com.example.allengineeringinone.ui.home.data.repository.DolarCotizationRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object HomeModel {

    @Provides
    @Singleton
    fun provideDolarCotizationRepository(): DolarCotizationRepository {
        return DolarCotizationRepositoryImpl()
    }
}