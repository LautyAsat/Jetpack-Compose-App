package com.example.allengineeringinone.ui.common.Chat.data.repository

import android.util.Log
import com.example.allengineeringinone.ui.common.Chat.data.model.Message
import com.google.firebase.Firebase
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.firestore.remote.Datastore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
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
            val snapshot = db.child("botResponses").child(question.trim().lowercase()).get().await()

            Log.i("CHAT_DEBUG", "${snapshot.value}")
            Result.success(snapshot.value as? String)
        }
        catch (e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun observeMessages(): Flow<String>  = callbackFlow {
        val listener = object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                snapshot.getValue(String::class.java)?.let { it ->
                    trySend(it)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
        }

        db.child("LiveChat").addChildEventListener(listener)

        awaitClose { db.child("LiveChat").removeEventListener(listener) }
    }

}


