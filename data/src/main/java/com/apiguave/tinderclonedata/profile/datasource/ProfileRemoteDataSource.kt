package com.apiguave.tinderclonedata.profile.datasource

import com.apiguave.tinderclonedata.api.picture.PictureApi
import com.apiguave.tinderclonedata.api.user.FirestoreUserProperties
import com.apiguave.tinderclonedata.api.user.UserApi
import com.apiguave.tinderclonedata.extension.toBoolean
import com.apiguave.tinderclonecompose.data.extension.toFirestoreOrientation
import com.apiguave.tinderclonedata.api.user.FirestoreUser
import com.apiguave.tinderclonedata.extension.toLongString
import com.apiguave.tinderclonedata.extension.toOrientation
import com.apiguave.tinderclonedata.extension.toProfile
import com.apiguave.tinderclonedata.profile.model.NewMatch
import com.apiguave.tinderclonedata.profile.model.Profile
import com.apiguave.tinderclonedata.picture.Picture
import com.apiguave.tinderclonedata.picture.RemotePicture
import com.apiguave.tinderclonedata.profile.model.CreateUserProfile
import com.apiguave.tinderclonedata.profile.model.Gender
import com.apiguave.tinderclonedata.profile.model.Orientation
import com.apiguave.tinderclonedata.profile.model.UserProfile
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class ProfileRemoteDataSource(private val userApi: UserApi, private val pictureApi: PictureApi) {
    suspend fun getUserProfile(userId: String): UserProfile {
        val currentUser = userApi.getUser(userId)
        val profilePictures = pictureApi.getPictures(currentUser.id, currentUser.pictures)
        return UserProfile(
            currentUser.id,
            currentUser.name,
            currentUser.birthDate!!.toDate().toLongString(),
            currentUser.bio,
            if(currentUser.male!!) Gender.MALE else Gender.FEMALE,
            currentUser.orientation!!.toOrientation(),
            currentUser.liked,
            currentUser.passed,
            profilePictures
        )
    }

    suspend fun createProfile(profile: CreateUserProfile) {
        val filenames = pictureApi.uploadPictures(profile.id, profile.pictures)
        userApi.createUser(
            profile.id,
            profile.name,
            profile.birthdate,
            profile.bio,
            profile.gender,
            profile.orientation,
            filenames.map { it.filename })
    }

    suspend fun updateProfile(
        currentProfile: UserProfile,
        bio: String,
        gender: Gender,
        orientation: Orientation,
        pictures: List<Picture>
    ): UserProfile {
        val arePicturesEqual = currentProfile.pictures == pictures
        return if(arePicturesEqual) {
            val (newProfile, data) = currentProfile.getModifiedData(bio, gender, orientation, currentProfile.pictures)
            if(data != null) userApi.updateUser(currentProfile.id, data)
            newProfile
        } else {
            val filenames = pictureApi.updatePictures(currentProfile.id, currentProfile.pictures, pictures)
            val (newProfile, data) = currentProfile.getModifiedData(bio, gender, orientation, filenames)
            if(data != null) userApi.updateUser(currentProfile.id, data)
            newProfile
        }
    }


    private fun UserProfile.getModifiedData(bio: String, gender: Gender, orientation: Orientation, pictures: List<RemotePicture>): Pair<UserProfile, Map<String, Any>?> {
        val data = mutableMapOf<String, Any>()
        var newUserProfile = this
        if(bio != this.bio){
            data[FirestoreUserProperties.bio] = bio
            newUserProfile = newUserProfile.copy(bio = bio)
        }
        if(gender != this.gender){
            data[FirestoreUserProperties.isMale] = gender.toBoolean()
            newUserProfile = newUserProfile.copy(gender = gender)
        }
        if(orientation != this.orientation){
            data[FirestoreUserProperties.orientation] = orientation.toFirestoreOrientation()
            newUserProfile = newUserProfile.copy(orientation = orientation)
        }

        if(pictures != this.pictures) {
            data[FirestoreUserProperties.pictures] = pictures.map { it.filename }
            newUserProfile = newUserProfile.copy(pictures = pictures)
        }

        return newUserProfile to (if(data.isEmpty()) null else data)
    }

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


}