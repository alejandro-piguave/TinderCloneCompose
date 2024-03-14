package com.apiguave.tinderclonecompose.data.message.datasource

import com.apiguave.tinderclonecompose.data.auth.exception.AuthException
import com.apiguave.tinderclonecompose.data.match.datasource.FirestoreMatchProperties
import com.apiguave.tinderclonecompose.data.message.repository.Message
import com.apiguave.tinderclonecompose.data.extension.getTaskResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class MessageRemoteDataSource {
    companion object {
        private const val MATCHES = "matches"
        private const val MESSAGES = "messages"
    }
    
    private val currentUserId: String
        get() = FirebaseAuth.getInstance().currentUser?.uid ?: throw AuthException("User not logged in")


    fun getMessages(matchId: String): Flow<List<Message>> = callbackFlow {
        // Reference to use in Firestore
        var eventsCollection: CollectionReference? = null
        try {
            eventsCollection = FirebaseFirestore.getInstance()
                .collection(MATCHES)
                .document(matchId).collection(MESSAGES)
        } catch (e: Throwable) {
            // If Firebase cannot be initialized, close the stream of data
            // flow consumers will stop collecting and the coroutine will resume
            close(e)
        }

        // Registers callback to firestore, which will be called on new events
        val subscription = eventsCollection?.orderBy(FirestoreMessageProperties.timestamp)?.addSnapshotListener { snapshot, _ ->
            if (snapshot == null) { return@addSnapshotListener }
            // Sends events to the flow! Consumers will get the new events
            try {
                val messages = snapshot.toObjects(FirestoreMessage::class.java).map {
                    val isSender = it.senderId == currentUserId
                    val text = it.message
                    Message(text, isSender)
                }
                trySend(messages)
            }catch (e: Exception){
                close(e)
            }
        }

        // The callback inside awaitClose will be executed when the flow is
        // either closed or cancelled.
        // In this case, remove the callback from Firestore
        awaitClose { subscription?.remove() }
    }

    suspend fun sendMessage(matchId: String, text: String){
        val data = FirestoreMessageProperties.toData(currentUserId, text)
        coroutineScope {
            val newMessageResult = async {
                FirebaseFirestore.getInstance()
                    .collection(MATCHES)
                    .document(matchId)
                    .collection(MESSAGES)
                    .add(data)
                    .getTaskResult()
            }
            val lastMessageResult = async {
                FirebaseFirestore.getInstance()
                    .collection(MATCHES)
                    .document(matchId)
                    .update(mapOf(FirestoreMatchProperties.lastMessage to text))
                    .getTaskResult()
            }
            newMessageResult.await()
            lastMessageResult.await()
        }
    }
}