package com.apiguave.tinderclonecompose.data.impl

import com.apiguave.tinderclonecompose.data.datasource.FirestoreRemoteDataSource
import com.apiguave.tinderclonecompose.data.extension.toProfile
import com.apiguave.tinderclonecompose.data.home.HomeRepository
import com.apiguave.tinderclonecompose.data.home.entity.NewMatch
import com.apiguave.tinderclonecompose.data.home.entity.Profile
import com.apiguave.tinderclonecompose.data.picture.repository.PictureRepository
import com.apiguave.tinderclonecompose.data.user.repository.User
import com.apiguave.tinderclonecompose.data.user.repository.UserRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class HomeRepositoryImpl(
    private val pictureRepository: PictureRepository,
    private val userRepository: UserRepository,
    private val firestoreDataSource: FirestoreRemoteDataSource,
): HomeRepository {

    override suspend fun swipeUser(profile: Profile, isLike: Boolean): NewMatch? {
        val matchModel = firestoreDataSource.swipeUser(profile.id, isLike)
        return matchModel?.let { model ->
            NewMatch(model.id, profile.id, profile.name, profile.pictures)
        }
    }

    override suspend fun getProfiles(): List<Profile> {
        //Fetch profiles
        val compatibleUsers = userRepository.getCompatibleUsers()
        val profiles = coroutineScope {
            val profiles = compatibleUsers.map { async { getProfile(it) } }
            profiles.awaitAll()
        }
        return profiles
    }

    private suspend fun getProfile(user: User): Profile {
        val pictures = if (user.pictures.isEmpty()) emptyList() else pictureRepository.getPictures(user)
        return user.toProfile(pictures.map { it.uri })
    }

}