package com.apiguave.core_firebase

import com.apiguave.core_firebase.extensions.getTaskResult
import com.apiguave.core_firebase.extensions.toDate
import com.apiguave.core_firebase.model.FirestoreMatchProperties
import com.apiguave.core_firebase.model.FirestoreUser
import com.apiguave.core_firebase.model.FirestoreUserProperties
import com.apiguave.tinderclonedata.source.firebase.model.FirestoreOrientation
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import java.time.LocalDate

object UserApi {

    private const val USERS = "users"
    private const val MATCHES = "matches"

    suspend fun createUser(
        userId: String,
        name: String,
        birthdate: LocalDate,
        bio: String,
        isMale: Boolean,
        orientation: FirestoreOrientation
    ) {
        val user = FirestoreUser(
            name = name,
            birthDate = birthdate.toDate(),
            bio = bio,
            male = isMale,
            orientation = orientation,
            pictures = emptyList(),
            liked = emptyList(),
            passed = emptyList()
        )
        FirebaseFirestore.getInstance().collection(USERS).document(userId).set(user).getTaskResult()
    }

    suspend fun userExists(): Boolean {
        val snapshot = FirebaseFirestore.getInstance().collection(USERS).document(AuthApi.userId!!).get().getTaskResult()
        return snapshot.exists()
    }

    suspend fun getUser(userId: String): FirestoreUser? {
        val snapshot = FirebaseFirestore.getInstance().collection(USERS).document(userId).get().getTaskResult()
        return snapshot.toObject<FirestoreUser>()
    }

    suspend fun getCompatibleUsers(currentUser: FirestoreUser): List<FirestoreUser> {
        val excludedUserIds = currentUser.liked + currentUser.passed + currentUser.id

        //Build query
        val searchQuery: Query = kotlin.run {
            var query: Query = FirebaseFirestore.getInstance().collection(USERS)
            if(currentUser.male != null) {
                query = query.whereNotEqualTo(
                    FirestoreUserProperties.orientation,
                    when (currentUser.male) {
                        true -> FirestoreOrientation.women.name
                        false -> FirestoreOrientation.men.name
                    }
                )
            }

            if (currentUser.orientation != FirestoreOrientation.both) {
                query = query.whereEqualTo(
                    FirestoreUserProperties.isMale,
                    currentUser.orientation == FirestoreOrientation.men
                )
            }

            query
        }

        val result = searchQuery.get().getTaskResult()
        //Filter documents
        return result.filter { !excludedUserIds.contains(it.id) }.mapNotNull { it.toObject<FirestoreUser>() }
    }

    suspend fun swipeUser(swipedUserId: String, isLike: Boolean): String? {
        FirebaseFirestore.getInstance()
            .collection(USERS)
            .document(AuthApi.userId!!)
            .update(mapOf((if (isLike) FirestoreUserProperties.liked else FirestoreUserProperties.passed) to FieldValue.arrayUnion(swipedUserId)))
            .getTaskResult()
        FirebaseFirestore.getInstance()
            .collection(USERS)
            .document(AuthApi.userId!!)
            .collection(FirestoreUserProperties.liked)
            .document(swipedUserId)
            .set(mapOf("exists" to true))
            .getTaskResult()

        val hasUserLikedBack = hasUserLikedBack(swipedUserId)
        if(hasUserLikedBack){
            val matchId = getMatchId(AuthApi.userId!!, swipedUserId)
            FieldValue.serverTimestamp()
            val data = FirestoreMatchProperties.toData(swipedUserId, AuthApi.userId!!)
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

    private suspend fun hasUserLikedBack(swipedUserId: String): Boolean{
        val currentUserId = AuthApi.userId!!
        val result = FirebaseFirestore.getInstance()
            .collection(USERS)
            .document(swipedUserId)
            .collection(FirestoreUserProperties.liked)
            .document(currentUserId)
            .get()
            .getTaskResult()
        return result.exists()
    }

    suspend fun updateUser(
        bio: String,
        gender: Boolean,
        orientation: FirestoreOrientation) {
        val data = mapOf(
            FirestoreUserProperties.bio to bio,
            FirestoreUserProperties.isMale to gender,
            FirestoreUserProperties.orientation to orientation
        )
        FirebaseFirestore.getInstance().collection(USERS).document(AuthApi.userId!!).update(data).getTaskResult()
    }


    suspend fun updateUserPictures(pictures: List<String>) {
        val data = mapOf(
            FirestoreUserProperties.pictures to pictures
        )
        FirebaseFirestore.getInstance().collection(USERS).document(AuthApi.userId!!).update(data).getTaskResult()
    }
}