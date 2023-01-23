package com.apiguave.tinderclonecompose.repository

import com.apiguave.tinderclonecompose.domain.FirestoreUser
import com.apiguave.tinderclonecompose.domain.Orientation
import com.apiguave.tinderclonecompose.extensions.getTaskResult
import com.apiguave.tinderclonecompose.extensions.toTimestamp
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDate

object FirestoreRepository {
    private const val USERS = "users"
    suspend fun createUserProfile(
        name: String,
        birthdate: LocalDate,
        bio: String,
        isMale: Boolean,
        orientation: Orientation,
        pictures: List<String>
    ) {
        val user = FirestoreUser(
            name = name,
            birthdate = birthdate.toTimestamp(),
            bio = bio,
            isMale = isMale,
            orientation = orientation,
            pictures = pictures,
            liked = emptyList(),
            passed = emptyList()
        )
        FirebaseFirestore.getInstance().collection(USERS).document(AuthRepository.userId!!).set(user).getTaskResult()
    }
}