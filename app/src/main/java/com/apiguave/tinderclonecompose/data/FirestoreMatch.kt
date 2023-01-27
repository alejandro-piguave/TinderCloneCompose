package com.apiguave.tinderclonecompose.data

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class FirestoreMatch(
    @DocumentId
    var id: String="",
    val usersMatched: List<String> = emptyList(),
    val timestamp: Timestamp? = null
)
object FirestoreMatchProperties{
    const val usersMatched = "usersMatched"
    const val timestamp = "timestamp"
}
