package com.apiguave.tinderclonecompose.data.repository

import com.apiguave.tinderclonecompose.data.datasource.FirestoreRemoteDataSource
import com.apiguave.tinderclonecompose.data.datasource.ProfileLocalDataSource
import com.apiguave.tinderclonecompose.data.datasource.StorageRemoteDataSource
import com.apiguave.tinderclonecompose.data.datasource.model.FirestoreUser
import com.apiguave.tinderclonecompose.domain.home.HomeRepository
import com.apiguave.tinderclonecompose.domain.home.entity.NewMatch
import com.apiguave.tinderclonecompose.domain.home.entity.Profile
import com.apiguave.tinderclonecompose.domain.profile.entity.FirebasePicture
import com.apiguave.tinderclonecompose.extensions.toProfile
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class HomeRepositoryImpl(
    private val storageDataSource: StorageRemoteDataSource,
    private val firestoreDataSource: FirestoreRemoteDataSource,
    private val profileLocalDataSource: ProfileLocalDataSource
): HomeRepository {


    override suspend fun swipeUser(profile: Profile, isLike: Boolean): NewMatch? {
        val matchModel = firestoreDataSource.swipeUser(profile.id, isLike)
        return matchModel?.let { model ->
            NewMatch(model.id, profile.id, profile.name, profile.pictures)
        }
    }

    override suspend fun getProfiles(): List<Profile> {
        //Fetch profiles
        val homeData = firestoreDataSource.getHomeData()
        profileLocalDataSource.userProfile = homeData.currentUser
        val profiles = coroutineScope {
            val profiles = homeData.compatibleUsers.map { async { getProfile(it) } }
            val userPictures = async { getUserPictures(homeData.currentUser) }
            profileLocalDataSource.userPictures = userPictures.await()
            profiles.awaitAll()
        }
        return profiles
    }

    private suspend fun getUserPictures(userModel: FirestoreUser): List<FirebasePicture> {
        return if (userModel.pictures.isEmpty()) emptyList() else storageDataSource.getPicturesFromUser(
            userModel.id,
            userModel.pictures
        )
    }

    private suspend fun getProfile(userModel: FirestoreUser): Profile {
        val pictures =
            if (userModel.pictures.isEmpty()) emptyList() else storageDataSource.getPicturesFromUser(
                userModel.id,
                userModel.pictures
            )
        return userModel.toProfile(pictures.map { it.uri })
    }

}