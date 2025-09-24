package com.example.allengineeringinone.ui.common.Chat.data.repository

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.remote.Datastore
import kotlinx.coroutines.tasks.await
import okhttp3.internal.cache.DiskLruCache
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatRepositoryImpl @Inject constructor(
    private val db: DatabaseReference
) : ChatRepository {
    override suspend fun getResponsesFor(question: String): Result<String?> {
        return try{
            val snapshot = db.child(question.trim().lowercase()).get().await()

            Log.i("CHAT_DEBUG", "${snapshot.value}")
            Result.success(snapshot.value as? String)
        }
        catch (e: Exception){
            Result.failure(e)
        }
    }


}