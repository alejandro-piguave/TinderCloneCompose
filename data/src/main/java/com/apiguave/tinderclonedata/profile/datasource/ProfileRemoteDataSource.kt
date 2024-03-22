package com.apiguave.tinderclonedata.profile.datasource

import com.apiguave.tinderclonedata.api.picture.PictureApi
import com.apiguave.tinderclonedata.api.user.FirestoreUserProperties
import com.apiguave.tinderclonedata.api.user.UserApi
import com.apiguave.tinderclonedata.extension.toBoolean
import com.apiguave.tinderclonecompose.data.extension.toFirestoreOrientation
import com.apiguave.tinderclonedata.extension.toLongString
import com.apiguave.tinderclonedata.extension.toOrientation
import com.apiguave.tinderclonedata.picture.Picture
import com.apiguave.tinderclonedata.picture.RemotePicture
import com.apiguave.tinderclonedata.profile.repository.CreateUserProfile
import com.apiguave.tinderclonedata.profile.repository.Gender
import com.apiguave.tinderclonedata.profile.repository.Orientation
import com.apiguave.tinderclonedata.profile.repository.UserProfile

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

}