package com.apiguave.tinderclonecompose.data.repository

import com.apiguave.tinderclonecompose.data.FirestoreMatchModel
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
        private const val MATCHES = "matches"
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
        val currentUser = getFirestoreUserModel(AuthRepository.userId)
        currentUser.male ?: throw FirestoreException("Couldn't find field 'isMale' for the current user.")
        val excludedUserIds = currentUser.liked + currentUser.passed

        //Build query
        val searchQuery: Query = kotlin.run {
            val query = FirebaseFirestore.getInstance().collection(USERS)
                .whereNotEqualTo(
                    FirestoreUserModel.orientation,
                    if (currentUser.male)
                        Orientation.women.name
                    else Orientation.men.name
                )
            if (currentUser.orientation != Orientation.both.name) {
                query.whereEqualTo(
                    FirestoreUserModel.isMale,
                    currentUser.orientation == Orientation.men.name
                )
            } else query
        }

        val result = searchQuery.get().getTaskResult()

        //Filter documents
        val filteredDocumentList = result.filter { it.id != currentUser.id && !excludedUserIds.contains(it.id) }

        return filteredDocumentList.mapNotNull { it.toObject() }
    }

    suspend fun getFirestoreMatchModels(): List<FirestoreMatchModel>{
        val query = FirebaseFirestore.getInstance().collection(MATCHES).whereArrayContains(FirestoreMatchModel.usersMatched, AuthRepository.userId)
        val result = query.get().getTaskResult()
        return result.mapNotNull { it.toObject() }
    }

    suspend fun getFirestoreUserModel(userId: String): FirestoreUserModel {
        val snapshot = FirebaseFirestore.getInstance().collection(USERS).document(userId).get().getTaskResult()
        return snapshot.toObject<FirestoreUserModel>() ?: throw FirestoreException("Couldn't convert document to object.")
    }
}