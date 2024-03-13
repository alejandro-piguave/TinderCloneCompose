package com.apiguave.tinderclonecompose.data.datasource

import com.apiguave.tinderclonecompose.data.datasource.model.*
import com.apiguave.tinderclonecompose.data.datasource.exception.FirestoreException
import com.apiguave.tinderclonecompose.domain.message.entity.Message
import com.apiguave.tinderclonecompose.domain.profile.entity.Orientation
import com.apiguave.tinderclonecompose.data.datasource.model.FirestoreUserList
import com.apiguave.tinderclonecompose.domain.account.exception.AuthException
import com.apiguave.tinderclonecompose.extensions.getTaskResult
import com.apiguave.tinderclonecompose.extensions.toTimestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.time.LocalDate

class FirestoreRemoteDataSource {
    companion object {
        private const val USERS = "users"
        private const val MATCHES = "matches"
        private const val MESSAGES = "messages"
    }
    
    private val currentUserId: String
        get() = FirebaseAuth.getInstance().currentUser?.uid ?: throw AuthException("User not logged in")

    suspend fun updateProfileData(data: Map<String, Any>){
        FirebaseFirestore.getInstance().collection(USERS).document(currentUserId).update(data).getTaskResult()
    }


    fun getMessages(matchId: String): Flow<List<Message>> = callbackFlow {
        // Reference to use in Firestore
        var eventsCollection: CollectionReference? = null
        try {
            eventsCollection = FirebaseFirestore.getInstance()
                .collection(MATCHES)
                .document(matchId).collection(MESSAGES)
        } catch (e: Throwable) {
            // If Firebase cannot be initialized, close the stream of data
            // flow consumers will stop collecting and the coroutine will resume
            close(e)
        }

        // Registers callback to firestore, which will be called on new events
        val subscription = eventsCollection?.orderBy(FirestoreMessageProperties.timestamp)?.addSnapshotListener { snapshot, _ ->
            if (snapshot == null) { return@addSnapshotListener }
            // Sends events to the flow! Consumers will get the new events
            try {
                val messages = snapshot.toObjects(FirestoreMessage::class.java).map {
                    val isSender = it.senderId == currentUserId
                    val text = it.message
                    Message(text, isSender)
                }
                trySend(messages)
            }catch (e: Exception){
                close(e)
            }
        }

        // The callback inside awaitClose will be executed when the flow is
        // either closed or cancelled.
        // In this case, remove the callback from Firestore
        awaitClose { subscription?.remove() }
    }

    suspend fun sendMessage(matchId: String, text: String){
        val data = FirestoreMessageProperties.toData(currentUserId, text)
        coroutineScope {
            val newMessageResult = async {
                FirebaseFirestore.getInstance()
                    .collection(MATCHES)
                    .document(matchId)
                    .collection(MESSAGES)
                    .add(data)
                    .getTaskResult()
            }
            val lastMessageResult = async {
                FirebaseFirestore.getInstance()
                    .collection(MATCHES)
                    .document(matchId)
                    .update(mapOf(FirestoreMatchProperties.lastMessage to text))
                    .getTaskResult()
            }
            newMessageResult.await()
            lastMessageResult.await()
        }
    }

    suspend fun swipeUser(swipedUserId: String, isLike: Boolean): FirestoreMatch? {
        FirebaseFirestore.getInstance()
            .collection(USERS)
            .document(currentUserId)
            .update(mapOf((if (isLike) FirestoreUserProperties.liked else FirestoreUserProperties.passed) to FieldValue.arrayUnion(swipedUserId)))
            .getTaskResult()
        FirebaseFirestore.getInstance()
            .collection(USERS)
            .document(currentUserId)
            .collection(FirestoreUserProperties.liked)
            .document(swipedUserId)
            .set(mapOf("exists" to true))
            .getTaskResult()

        val hasUserLikedBack = hasUserLikedBack(swipedUserId)
        if(hasUserLikedBack){
            val matchId = getMatchId(currentUserId, swipedUserId)
            FieldValue.serverTimestamp()
            val data = FirestoreMatchProperties.toData(swipedUserId, currentUserId)
            FirebaseFirestore.getInstance()
                .collection(MATCHES)
                .document(matchId)
                .set(data)
                .getTaskResult()

            val result = FirebaseFirestore.getInstance()
                .collection(MATCHES)
                .document(matchId)
                .get()
                .getTaskResult()
            return result.toObject()
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
            .document(currentUserId)
            .get()
            .getTaskResult()
        return result.exists()
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
        val user = FirestoreUser(
            name = name,
            birthDate = birthdate.toTimestamp(),
            bio = bio,
            male = isMale,
            orientation = orientation,
            pictures = pictures,
            liked = emptyList(),
            passed = emptyList()
        )
        FirebaseFirestore.getInstance().collection(USERS).document(userId).set(user).getTaskResult()
    }

    suspend fun getUserList(): FirestoreUserList {
        //Get current user information
        val currentUser = getFirestoreUserModel(currentUserId)
        currentUser.male ?: throw FirestoreException("Couldn't find field 'isMale' for the current user.")
        val excludedUserIds = currentUser.liked + currentUser.passed + currentUserId

        //Build query
        val searchQuery: Query = kotlin.run {
            val query = FirebaseFirestore.getInstance().collection(USERS)
                .whereNotEqualTo(
                    FirestoreUserProperties.orientation,
                    if (currentUser.male)
                        Orientation.women.name
                    else Orientation.men.name
                )
            if (currentUser.orientation != Orientation.both) {
                query.whereEqualTo(
                    FirestoreUserProperties.isMale,
                    currentUser.orientation == Orientation.men
                )
            } else query
        }

        val result = searchQuery.get().getTaskResult()
        //Filter documents
        val compatibleUsers: List<FirestoreUser> = result.filter { !excludedUserIds.contains(it.id) }.mapNotNull { it.toObject() }
        return FirestoreUserList(currentUser, compatibleUsers)
    }

    suspend fun getFirestoreMatchModels(): List<FirestoreMatch> {
        val query = FirebaseFirestore.getInstance().collection(MATCHES)
            .whereArrayContains(FirestoreMatchProperties.usersMatched, currentUserId)
        val result = query.get().getTaskResult()
        return result.toObjects(FirestoreMatch::class.java)
    }

    suspend fun getFirestoreUserModel(userId: String): FirestoreUser {
        val snapshot = FirebaseFirestore.getInstance().collection(USERS).document(userId).get().getTaskResult()
        return snapshot.toObject<FirestoreUser>() ?: throw FirestoreException("Document doesn't exist")
    }
}