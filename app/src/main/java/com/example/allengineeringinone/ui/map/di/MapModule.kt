package com.example.allengineeringinone.ui.map.di

import com.example.allengineeringinone.ui.map.data.repository.MapRepository
import com.example.allengineeringinone.ui.map.data.repository.MapRepositoryImpl
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MapModule {

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return Firebase.firestore
    }

    @Provides
    @Singleton
    fun provideMapRepository(firestore: FirebaseFirestore): MapRepository {
        return MapRepositoryImpl(firestore)
    }
}