package com.apiguave.tinderclonecompose.domain

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class FirestoreUser(
    @DocumentId
    var id: String?=null,
    val name: String,
    val birthdate: Timestamp,
    val bio: String,
    @field:JvmField
    val isMale: Boolean,
    val orientation: Orientation,
    val pictures: List<String>,
    val liked: List<String>,
    val passed: List<String>
)