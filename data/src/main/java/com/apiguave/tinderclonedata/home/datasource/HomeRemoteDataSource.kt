package com.apiguave.tinderclonedata.home.datasource

import com.apiguave.tinderclonedata.api.picture.PictureApi
import com.apiguave.tinderclonedata.api.user.FirestoreUser
import com.apiguave.tinderclonedata.api.user.UserApi
import com.apiguave.tinderclonedata.extension.toProfile
import com.apiguave.tinderclonedata.home.repository.NewMatch
import com.apiguave.tinderclonedata.home.repository.Profile
import com.apiguave.tinderclonedata.profile.repository.CreateUserProfile
import com.apiguave.tinderclonedata.profile.repository.UserProfile
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class HomeRemoteDataSource(private val userApi: UserApi, private val pictureApi: PictureApi) {

    suspend fun getProfiles(user: UserProfile): List<Profile> {
        val users = userApi.getCompatibleUsers(user.id, user.gender, user.orientation, user.liked, user.passed)
        val profiles = coroutineScope {
            val profiles = users.map { async { getProfile(it) } }
            profiles.awaitAll()
        }
        return profiles
    }

    private suspend fun getProfile(user: FirestoreUser): Profile {
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