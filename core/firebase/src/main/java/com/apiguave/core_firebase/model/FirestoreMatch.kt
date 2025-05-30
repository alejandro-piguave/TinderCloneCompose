package com.apiguave.core_firebase.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.FieldValue
import java.util.Date

data class FirestoreMatch(
    @DocumentId
    var id: String="",
    val usersMatched: List<String> = emptyList(),
    val timestamp: Date? = null,
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
