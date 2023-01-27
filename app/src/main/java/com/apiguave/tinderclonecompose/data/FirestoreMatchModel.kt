package com.apiguave.tinderclonecompose.data

import com.google.firebase.Timestamp

data class FirestoreMatchModel(
    var id: String? = null,
    val usersMatched: List<String> = emptyList(),
    val timestamp: Timestamp? = null
){
    companion object Properties{
        const val usersMatched = "usersMatched"
    }
}