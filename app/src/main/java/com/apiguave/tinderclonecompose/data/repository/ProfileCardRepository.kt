package com.apiguave.tinderclonecompose.data.repository

import com.apiguave.tinderclonecompose.data.*
import com.apiguave.tinderclonecompose.data.datasource.FirestoreDataSource
import com.apiguave.tinderclonecompose.data.datasource.StorageDataSource
import com.apiguave.tinderclonecompose.extensions.toCurrentProfile
import com.apiguave.tinderclonecompose.extensions.toProfile
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

object ProfileCardRepository {
    private val storageDataSource = StorageDataSource()
    private val firestoreDataSource = FirestoreDataSource()

    suspend fun swipeUser(profile: Profile, isLike: Boolean): NewMatch? {
        val matchModel = firestoreDataSource.swipeUser(profile.id, isLike)
        return matchModel?.let { model ->
            NewMatch(model.id, profile.id, profile.name, profile.pictures)
        }
    }

    suspend fun getProfiles(): ProfileList {
        val userList = firestoreDataSource.getUserList()
        //Fetch profiles
        val profileList = coroutineScope {
            val profiles = userList.compatibleUsers.map { async { getProfile(it) } }
            val currentProfile = async { getCurrentProfile(userList.currentUser) }
            ProfileList(currentProfile.await(), profiles.awaitAll())
        }
        return profileList
    }

    private suspend fun getCurrentProfile(userModel: FirestoreUser): CurrentProfile {
        val pictures = if (userModel.pictures.isEmpty()) emptyList() else storageDataSource.getPicturesFromUser(userModel.id, userModel.pictures)
        return userModel.toCurrentProfile(pictures)
    }

    private suspend fun getProfile(userModel: FirestoreUser): Profile {
        val pictures = if (userModel.pictures.isEmpty()) emptyList() else storageDataSource.getPicturesFromUser(userModel.id, userModel.pictures)
        return userModel.toProfile(pictures.map { it.uri })
    }

}