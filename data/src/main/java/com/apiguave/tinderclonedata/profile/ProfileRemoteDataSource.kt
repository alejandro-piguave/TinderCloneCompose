package com.apiguave.tinderclonedata.profile

import android.net.Uri
import com.apiguave.tinderclonedata.api.picture.PictureApi
import com.apiguave.tinderclonedata.api.user.FirestoreUserProperties
import com.apiguave.tinderclonedata.api.user.UserApi
import com.apiguave.tinderclonedata.extension.toBoolean
import com.apiguave.tinderclonecompose.data.extension.toFirestoreOrientation
import com.apiguave.tinderclonedata.api.user.FirestoreUser
import com.apiguave.tinderclonedata.extension.toLongString
import com.apiguave.tinderclonedata.extension.toOrientation
import com.apiguave.tinderclonedata.extension.toProfile
import com.apiguave.tinderclonedomain.profile.LocalPicture
import com.apiguave.tinderclonedomain.profile.NewMatch
import com.apiguave.tinderclonedomain.profile.Profile
import com.apiguave.tinderclonedomain.profile.Picture
import com.apiguave.tinderclonedomain.profile.RemotePicture
import com.apiguave.tinderclonedomain.profile.Gender
import com.apiguave.tinderclonedomain.profile.Orientation
import com.apiguave.tinderclonedomain.profile.UserProfile
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import java.time.LocalDate

class ProfileRemoteDataSource(private val userApi: UserApi, private val pictureApi: PictureApi) {
    suspend fun getUserProfile(): UserProfile {
        val currentUser = userApi.getUser()
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

    suspend fun createProfile(
        userId: String,
        name: String,
        birthdate: LocalDate,
        bio: String,
        gender: Gender,
        orientation: Orientation,
        pictures: List<LocalPicture>
    ) {
        val filenames = pictureApi.uploadPictures(userId, pictures.map { Uri.parse(it.uri) })
        userApi.createUser(
            userId,
            name,
            birthdate,
            bio,
            gender,
            orientation,
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
            val filenames = updatePictures(currentProfile.id, currentProfile.pictures, pictures)
            val (newProfile, data) = currentProfile.getModifiedData(bio, gender, orientation, filenames)
            if(data != null) userApi.updateUser(currentProfile.id, data)
            newProfile
        }
    }

    private suspend fun updatePictures(userId: String, outdatedPictures: List<RemotePicture>, updatedPictures: List<Picture>): List<RemotePicture>{
        return coroutineScope {
            //This is a list of the pictures that were already uploaded but that have been removed from the profile.
            val picturesToDelete: List<RemotePicture> =
                updatedPictures
                    .filter { it is RemotePicture && !outdatedPictures.contains(it) }
                    .map { it as RemotePicture }

            val pictureDeletionResult = async {
                if(picturesToDelete.isEmpty()) Unit
                else pictureApi.deletePictures(userId, picturesToDelete.map { it.filename })
            }

            val pictureUploadResult = updatedPictures.map {
                async {
                    when(it){
                        //If the picture was already uploaded, simply return its file name.
                        is RemotePicture -> it
                        //Otherwise uploaded and return it's new file name
                        is LocalPicture -> pictureApi.uploadPicture(userId, Uri.parse(it.uri))
                    }
                }
            }

            pictureDeletionResult.await()
            //Returns a list of the new filenames
            pictureUploadResult.awaitAll()
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

    suspend fun passProfile(profile: Profile) {
        userApi.swipeUser(profile.id, false)
    }

    suspend fun likeProfile(profile: Profile): NewMatch? {
        return userApi.swipeUser(profile.id, true)?.let { model ->
            NewMatch(model.id, model.id, profile.name, profile.pictures)
        }
    }

}