package com.apiguave.tinderclonecompose.data

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.FieldValue

data class FirestoreMatch(
    @DocumentId
    var id: String="",
    val usersMatched: List<String> = emptyList(),
    val timestamp: Timestamp? = null,
    val lastMessage: String?=null
)
object FirestoreMatchProperties{
    const val usersMatched = "usersMatched"
    const val timestamp = "timestamp"
    const val lastMessage = "lastMessage"

    fun toData(user1: String, user2:String): Map<String, Any>{
        return mapOf(
            usersMatched to listOf(user1, user2),
            timestamp to FieldValue.serverTimestamp()
        )
    }
}
