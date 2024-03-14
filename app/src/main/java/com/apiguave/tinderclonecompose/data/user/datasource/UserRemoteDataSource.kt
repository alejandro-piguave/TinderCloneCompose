package com.apiguave.tinderclonecompose.data.user.datasource

import com.apiguave.tinderclonecompose.data.account.exception.AuthException
import com.apiguave.tinderclonecompose.data.datasource.exception.FirestoreException
import com.apiguave.tinderclonecompose.data.extension.getTaskResult
import com.apiguave.tinderclonecompose.data.extension.toBoolean
import com.apiguave.tinderclonecompose.data.extension.toFirestoreOrientation
import com.apiguave.tinderclonecompose.data.extension.toTimestamp
import com.apiguave.tinderclonecompose.data.extension.toUser
import com.apiguave.tinderclonecompose.data.profile.repository.Gender
import com.apiguave.tinderclonecompose.data.profile.repository.Orientation
import com.apiguave.tinderclonecompose.data.user.repository.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import java.time.LocalDate

class UserRemoteDataSource {
    companion object {
        private const val USERS = "users"
    }
    private val currentUserId: String
        get() = FirebaseAuth.getInstance().currentUser?.uid ?: throw AuthException("User not logged in")

    private suspend fun getFirebaseUser(userId: String): FirestoreUser {
        val snapshot = FirebaseFirestore.getInstance().collection(USERS).document(userId).get().getTaskResult()
        return snapshot.toObject<FirestoreUser>() ?: throw FirestoreException("Document doesn't exist")
    }

    suspend fun getUser(userId: String): User {
        val firebaseUser = getFirebaseUser(userId)
        return firebaseUser.toUser()
    }

    suspend fun updateCurrentUser(data: Map<String, Any>){
        FirebaseFirestore.getInstance().collection(USERS).document(currentUserId).update(data).getTaskResult()
    }

    suspend fun getCompatibleUsers(user: User): List<User> {
        val excludedUserIds = user.liked + user.passed + user.id

        //Build query
        val searchQuery: Query = kotlin.run {
            val query = FirebaseFirestore.getInstance().collection(USERS)
                .whereNotEqualTo(
                    FirestoreUserProperties.orientation,
                    when (user.gender) {
                        Gender.MALE -> FirestoreOrientation.women.name
                        Gender.FEMALE -> FirestoreOrientation.men.name
                    })
            if (user.orientation != Orientation.BOTH) {
                query.whereEqualTo(
                    FirestoreUserProperties.isMale,
                    user.orientation == Orientation.MEN
                )
            } else query
        }

        val result = searchQuery.get().getTaskResult()
        //Filter documents
        val compatibleUsers: List<FirestoreUser> = result.filter { !excludedUserIds.contains(it.id) }.mapNotNull { it.toObject() }
        return compatibleUsers.map { it.toUser() }
    }

    suspend fun createUser(
        userId: String,
        name: String,
        birthdate: LocalDate,
        bio: String,
        gender: Gender,
        orientation: Orientation,
        pictures: List<String>
    ) {
        val user = FirestoreUser(
            name = name,
            birthDate = birthdate.toTimestamp(),
            bio = bio,
            male = gender.toBoolean(),
            orientation = orientation.toFirestoreOrientation(),
            pictures = pictures,
            liked = emptyList(),
            passed = emptyList()
        )
        FirebaseFirestore.getInstance().collection(USERS).document(userId).set(user).getTaskResult()
    }
}