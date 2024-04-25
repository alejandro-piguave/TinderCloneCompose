package com.apiguave.tinderclonedata.source.api.user

import com.apiguave.tinderclonedata.source.api.match.FirestoreMatchProperties
import com.apiguave.tinderclonedata.source.extension.getTaskResult
import com.apiguave.tinderclonedata.source.extension.toBoolean
import com.apiguave.tinderclonedata.source.extension.toFirestoreOrientation
import com.apiguave.tinderclonedata.source.extension.toTimestamp
import com.apiguave.tinderclonedata.source.local.AuthProvider
import com.apiguave.tinderclonedomain.profile.Gender
import com.apiguave.tinderclonedomain.profile.Orientation
import com.apiguave.tinderclonedata.source.api.user.exception.FirestoreException
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

    /*
       This property is stored due to the particularities of Firebase.
       With a traditional backend application, for queries that need the data of the current user,
       we would already have access to that data inside of the backend so there would be no need to pass it,
       however, we can't do this with Firebase restrictions, so in order to avoid the cost of performing an extra query
       we just store it in memory so that it stays available when needed.
     */
    private var currentUser: FirestoreUser? = null

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

    //Get the signed in user
    suspend fun getUser(): FirestoreUser {
        return currentUser ?: getUser(authProvider.userId!!)
    }

    suspend fun getUser(userId: String): FirestoreUser {
        val snapshot = FirebaseFirestore.getInstance().collection(USERS).document(userId).get().getTaskResult()
        return snapshot.toObject<FirestoreUser>() ?: throw FirestoreException("Document doesn't exist")
    }

    suspend fun getCompatibleUsers(): List<FirestoreUser> {
        val currentUser = getUser()
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

        val hasUserLikedBack = hasUserLikedBack(swipedUserId)
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

    private suspend fun hasUserLikedBack(swipedUserId: String): Boolean{
        val result = FirebaseFirestore.getInstance()
            .collection(USERS)
            .document(swipedUserId)
            .collection(FirestoreUserProperties.liked)
            .document(authProvider.userId!!)
            .get()
            .getTaskResult()
        return result.exists()
    }

    suspend fun updateUser(
        bio: String,
        gender: Boolean,
        orientation: FirestoreOrientation,
        pictures: List<String>) {
        val data = mapOf(
            FirestoreUserProperties.bio to bio,
            FirestoreUserProperties.isMale to gender,
            FirestoreUserProperties.orientation to orientation,
            FirestoreUserProperties.pictures to pictures
        )
        FirebaseFirestore.getInstance().collection(USERS).document(authProvider.userId!!).update(data).getTaskResult()
        currentUser = currentUser?.copy(bio = bio, male = gender, orientation = orientation, pictures = pictures)
    }
}