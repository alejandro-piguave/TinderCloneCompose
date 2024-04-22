package com.apiguave.tinderclonedata.api.user

import com.apiguave.tinderclonedata.api.match.FirestoreMatchProperties
import com.apiguave.tinderclonedata.extension.getTaskResult
import com.apiguave.tinderclonedata.extension.toBoolean
import com.apiguave.tinderclonedata.extension.toFirestoreOrientation
import com.apiguave.tinderclonedata.extension.toTimestamp
import com.apiguave.tinderclonedata.api.auth.AuthProvider
import com.apiguave.tinderclonedomain.profile.Gender
import com.apiguave.tinderclonedomain.profile.Orientation
import com.apiguave.tinderclonedata.api.user.exception.FirestoreException
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import java.time.LocalDate

class UserApi(private val authProvider: AuthProvider) {

    companion object {
        private const val USERS = "users"
        private const val MATCHES = "matches"
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

    suspend fun getUser(): FirestoreUser = getUser(authProvider.userId!!)

    suspend fun getUser(userId: String): FirestoreUser {
        val snapshot = FirebaseFirestore.getInstance().collection(USERS).document(userId).get().getTaskResult()
        return snapshot.toObject<FirestoreUser>() ?: throw FirestoreException("Document doesn't exist")
    }

    suspend fun getCompatibleUsers(
        userId: String,
        gender: Gender,
        orientation: Orientation,
        liked: List<String>,
        passed: List<String>
    ): List<FirestoreUser> {
        val excludedUserIds = liked + passed + userId

        //Build query
        val searchQuery: Query = kotlin.run {
            val query = FirebaseFirestore.getInstance().collection(USERS)
                .whereNotEqualTo(
                    FirestoreUserProperties.orientation,
                    when (gender) {
                        Gender.MALE -> FirestoreOrientation.women.name
                        Gender.FEMALE -> FirestoreOrientation.men.name
                    }
                )
            if (orientation != Orientation.BOTH) {
                query.whereEqualTo(
                    FirestoreUserProperties.isMale,
                    orientation == Orientation.MEN
                )
            } else query
        }

        val result = searchQuery.get().getTaskResult()
        //Filter documents
        return result.filter { !excludedUserIds.contains(it.id) }.mapNotNull { it.toObject<FirestoreUser>() }
    }

    suspend fun swipeUser(swipedUserId: String, isLike: Boolean): String? {
        FirebaseFirestore.getInstance()
            .collection(USERS)
            .document(authProvider.userId!!)
            .update(mapOf((if (isLike) FirestoreUserProperties.liked else FirestoreUserProperties.passed) to FieldValue.arrayUnion(swipedUserId)))
            .getTaskResult()
        FirebaseFirestore.getInstance()
            .collection(USERS)
            .document(authProvider.userId!!)
            .collection(FirestoreUserProperties.liked)
            .document(swipedUserId)
            .set(mapOf("exists" to true))
            .getTaskResult()

        val hasUserLikedBack = hasUserLikedBack(authProvider.userId!!, swipedUserId)
        if(hasUserLikedBack){
            val matchId = getMatchId(authProvider.userId!!, swipedUserId)
            FieldValue.serverTimestamp()
            val data = FirestoreMatchProperties.toData(swipedUserId, authProvider.userId!!)
            FirebaseFirestore.getInstance()
                .collection(MATCHES)
                .document(matchId)
                .set(data)
                .getTaskResult()


            return matchId
        }
        return null
    }


    private fun getMatchId(userId1: String, userId2: String): String{
        return if(userId1 > userId2){
            userId1 + userId2
        } else userId2 + userId1
    }

    private suspend fun hasUserLikedBack(userId: String, swipedUserId: String): Boolean{
        val result = FirebaseFirestore.getInstance()
            .collection(USERS)
            .document(swipedUserId)
            .collection(FirestoreUserProperties.liked)
            .document(userId)
            .get()
            .getTaskResult()
        return result.exists()
    }

    suspend fun updateUser(userId: String, data: Map<String, Any>){
        FirebaseFirestore.getInstance().collection(USERS).document(userId).update(data).getTaskResult()
    }
}