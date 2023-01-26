package com.apiguave.tinderclonecompose.data

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class FirestoreUserModel(
    @DocumentId
    var id: String?=null,
    val name: String?=null,
    val birthDate: Timestamp?=null,
    val bio: String?=null,
    @field:JvmField
    val male: Boolean?=null,
    val orientation: String?=null,
    val pictures: List<String> = emptyList(),
    val liked: List<String> = emptyList(),
    val passed: List<String> = emptyList()
){
    companion object Properties{
        const val orientation = "orientation"
        const val isMale = "male"
    }
}