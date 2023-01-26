package com.apiguave.tinderclonecompose.data.repository

import com.apiguave.tinderclonecompose.data.FirestoreUserModel
import com.apiguave.tinderclonecompose.data.Orientation
import com.apiguave.tinderclonecompose.extensions.getTaskResult
import com.apiguave.tinderclonecompose.extensions.toTimestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import java.time.LocalDate

class FirestoreRepository {
    companion object{
        private const val USERS = "users"
    }
    suspend fun createUserProfile(
        userId: String,
        name: String,
        birthdate: LocalDate,
        bio: String,
        isMale: Boolean,
        orientation: Orientation,
        pictures: List<String>
    ) {
        val user = FirestoreUserModel(
            name = name,
            birthDate = birthdate.toTimestamp(),
            bio = bio,
            male = isMale,
            orientation = orientation.name,
            pictures = pictures,
            liked = emptyList(),
            passed = emptyList()
        )
        FirebaseFirestore.getInstance().collection(USERS).document(userId).set(user).getTaskResult()
    }

    suspend fun getCompatibleUsers(): List<FirestoreUserModel> {
        //Get current user information
        val currentFirestoreUserModel = getFirestoreUserModel(AuthRepository.userId!!)
        val excludedUserIds = currentFirestoreUserModel.liked + currentFirestoreUserModel.passed

        //Build query
        val searchQuery: Query = kotlin.run {
            val query = FirebaseFirestore.getInstance().collection(USERS)
                .whereNotEqualTo(
                    FirestoreUserModel.orientation,
                    if (currentFirestoreUserModel.male!!)
                        Orientation.women.name
                    else Orientation.men.name
                )
            if (currentFirestoreUserModel.orientation != Orientation.both.name) {
                query.whereEqualTo(
                    FirestoreUserModel.isMale,
                    currentFirestoreUserModel.orientation == Orientation.men.name
                )
            } else query
        }

        val result = searchQuery.get().getTaskResult()

        //Filter documents
        val filteredDocumentList =
            result.filter { it.id != currentFirestoreUserModel.id!! && !excludedUserIds.contains(it.id) }

        return filteredDocumentList.mapNotNull { it.toObject() }
    }

    private suspend fun getFirestoreUserModel(userId: String): FirestoreUserModel {
        val snapshot =
            FirebaseFirestore.getInstance().collection(USERS).document(userId).get().getTaskResult()
        return snapshot.toObject<FirestoreUserModel>()!!
    }
}