package com.apiguave.tinderclonedata.source.firebase.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue

data class FirestoreMessage(
    val message: String="",
    val timestamp: Timestamp? = null,
    val senderId: String=""
)

object FirestoreMessageProperties{
    const val message = "message"
    const val timestamp = "timestamp"
    const val senderId = "senderId"

    fun toData(userId: String, text: String): Map<String, Any>{
        return mapOf(
            message to text,
            senderId to userId,
            timestamp to FieldValue.serverTimestamp()
        )
    }
}