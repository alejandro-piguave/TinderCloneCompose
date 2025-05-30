package com.apiguave.core_firebase.model

import com.apiguave.tinderclonedata.source.firebase.model.FirestoreOrientation
import com.google.firebase.firestore.DocumentId
import java.util.Date

data class FirestoreUser(
    @DocumentId
    var id: String="",
    val name: String="",
    val birthDate: Date?=null,
    val bio: String="",
    @field:JvmField
    val male: Boolean?=null,
    val orientation: FirestoreOrientation?=null,
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
