package com.apiguave.tinderclonecompose.data.repository

import android.util.Log
import com.apiguave.tinderclonecompose.data.FirestoreUserModel
import com.apiguave.tinderclonecompose.data.Orientation
import com.apiguave.tinderclonecompose.data.Profile
import com.apiguave.tinderclonecompose.extensions.getTaskResult
import com.apiguave.tinderclonecompose.extensions.toAge
import com.apiguave.tinderclonecompose.extensions.toTimestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
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
        FirebaseFirestore.getInstance().collection(USERS).document(AuthRepository.userId!!).set(user).getTaskResult()
    }

    suspend fun getProfiles(): List<Profile>{
        //Get current user information
        val currentFirestoreUserModel = getFirestoreUserModel(AuthRepository.userId!!)
        val excludedUserIds = currentFirestoreUserModel.liked + currentFirestoreUserModel.passed

        //Build query
        val searchQuery: Query = kotlin.run {
            val query = FirebaseFirestore.getInstance().collection(USERS)
                .whereNotEqualTo(
                    FirestoreUserModel.orientation,
                    if(currentFirestoreUserModel.male!!)
                        Orientation.women.name
                    else Orientation.men.name
                )
            if(currentFirestoreUserModel.orientation != Orientation.both.name){
                query.whereEqualTo(FirestoreUserModel.isMale, currentFirestoreUserModel.orientation == Orientation.men.name)
            } else query
        }

        val result = searchQuery.get().getTaskResult()

        Log.i("FirestoreRepository", "query result is empty? ${result.isEmpty}")
        Log.i("FirestoreRepository", "query result = ${result.documents}")

        //Filter documents
        val filteredDocumentList = result.filter { it.id != currentFirestoreUserModel.id!! && !excludedUserIds.contains(it.id) }
        val firestoreUserModels = filteredDocumentList.mapNotNull { it.toObject<FirestoreUserModel>() }

        Log.i("FirestoreRepository", "filteredDocumentList is empty? ${filteredDocumentList.isEmpty()}")
        Log.i("FirestoreRepository", "firestoreUserModels is empty? ${firestoreUserModels.isEmpty()}")
        Log.i("FirestoreRepository", "firestoreUserModels = $firestoreUserModels")
        //Fetch uris
        val profileUris = coroutineScope {
            firestoreUserModels.map { async{ StorageRepository.getUrisFromUser(it.id!!, it.pictures )} }.awaitAll()
        }
        Log.i("FirestoreRepository", "profileUris is empty? ${profileUris.isEmpty()}")
        Log.i("FirestoreRepository", "profileUris = $profileUris")


        //Merge both and return a list of profiles
        return firestoreUserModels.zip(profileUris) { userModel, uris ->
            Profile(userModel.id!!, userModel.name ?: "", userModel.birthDate!!.toAge(), uris)
        }
    }

    suspend fun getProfile(userId: String): Profile{
        val firestoreUserModel =  getFirestoreUserModel(userId)
        val uris = StorageRepository.getUrisFromUser(userId, firestoreUserModel.pictures)

        return Profile(firestoreUserModel.id!!, firestoreUserModel.name ?: "", firestoreUserModel.birthDate!!.toAge(), uris )
    }
    private suspend fun getFirestoreUserModel(userId: String): FirestoreUserModel {
        val snapshot =
            FirebaseFirestore.getInstance().collection(USERS).document(userId).get().getTaskResult()
        return snapshot.toObject<FirestoreUserModel>()!!
    }
}