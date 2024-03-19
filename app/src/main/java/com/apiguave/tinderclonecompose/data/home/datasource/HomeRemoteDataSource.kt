package com.apiguave.tinderclonecompose.data.home.datasource

import com.apiguave.tinderclonecompose.data.api.picture.PictureApi
import com.apiguave.tinderclonecompose.data.api.user.UserApi
import com.apiguave.tinderclonecompose.data.extension.toProfile
import com.apiguave.tinderclonecompose.data.home.repository.NewMatch
import com.apiguave.tinderclonecompose.data.home.repository.Profile
import com.apiguave.tinderclonecompose.data.profile.repository.CreateUserProfile
import com.apiguave.tinderclonecompose.data.user.repository.User
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class HomeRemoteDataSource(private val userApi: UserApi, private val pictureApi: PictureApi) {

    suspend fun getProfiles(user: User): List<Profile> {
        val users = userApi.getCompatibleUsers(user)
        val profiles = coroutineScope {
            val profiles = users.map { async { getProfile(it) } }
            profiles.awaitAll()
        }
        return profiles
    }

    private suspend fun getProfile(user: User): Profile {
        val pictures = pictureApi.getPictures(user.id, user.pictures)
        return user.toProfile(pictures.map { it.uri })
    }

    suspend fun passProfile(currentUserId: String, profile: Profile) {
        userApi.swipeUser(currentUserId, profile.id, false)
    }

    suspend fun likeProfile(currentUserId: String, profile: Profile): NewMatch? {
        return userApi.swipeUser(currentUserId, profile.id, true)?.let { model ->
            NewMatch(model.id, model.id, profile.name, profile.pictures)
        }
    }

    suspend fun createProfile(userId: String, profile: CreateUserProfile) {
        val filenames = pictureApi.uploadPictures(userId, profile.pictures)
        userApi.createUser(
            userId,
            profile.name,
            profile.birthdate,
            profile.bio,
            profile.gender,
            profile.orientation,
            filenames.map { it.filename })
    }
}