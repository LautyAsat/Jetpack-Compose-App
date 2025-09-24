package com.example.allengineeringinone.ui.common.Chat.di

import com.example.allengineeringinone.ui.common.Chat.data.repository.ChatRepository
import com.example.allengineeringinone.ui.common.Chat.data.repository.ChatRepositoryImpl
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ChatModel {

    @Provides
    @Singleton
    fun provideFirebaseFireRefernce(): DatabaseReference {
        return Firebase.database.reference
    }

    @Provides
    @Singleton
    fun provideChatRepository(
        firebaseReference: DatabaseReference
    ): ChatRepository {
        return ChatRepositoryImpl(firebaseReference)
    }

}