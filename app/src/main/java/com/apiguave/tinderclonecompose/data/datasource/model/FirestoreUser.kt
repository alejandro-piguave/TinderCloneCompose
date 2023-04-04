package com.apiguave.tinderclonecompose.data.datasource.model

import com.apiguave.tinderclonecompose.data.repository.model.Orientation
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class FirestoreUser(
    @DocumentId
    var id: String="",
    val name: String="",
    val birthDate: Timestamp?=null,
    val bio: String="",
    @field:JvmField
    val male: Boolean?=null,
    val orientation: Orientation?=null,
    val pictures: List<String> = emptyList(),
    val liked: List<String> = emptyList(),
    val passed: List<String> = emptyList()
)

object FirestoreUserProperties {
    // The property values should reflect the actual name of the data class
    // property they are referencing. This is done so in order to keep track
    // of the property names from a single place.
    const val orientation = "orientation"
    const val isMale = "male"
    const val pictures = "pictures"
    const val liked = "liked"
    const val passed = "passed"
    const val bio = "bio"
}
