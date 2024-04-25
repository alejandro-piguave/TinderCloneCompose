package com.apiguave.tinderclonedata.source.profile

import android.net.Uri
import com.apiguave.tinderclonedata.repository.profile.ProfileRemoteDataSource
import com.apiguave.tinderclonedata.source.api.auth.AuthProvider
import com.apiguave.tinderclonedata.source.api.picture.PictureApi
import com.apiguave.tinderclonedata.source.api.user.UserApi
import com.apiguave.tinderclonedata.source.extension.toBoolean
import com.apiguave.tinderclonedata.source.extension.toFirestoreOrientation
import com.apiguave.tinderclonedata.source.api.user.FirestoreUser
import com.apiguave.tinderclonedata.source.extension.toAge
import com.apiguave.tinderclonedata.source.extension.toLongString
import com.apiguave.tinderclonedata.source.extension.toOrientation
import com.apiguave.tinderclonedomain.profile.LocalPicture
import com.apiguave.tinderclonedomain.profile.Profile
import com.apiguave.tinderclonedomain.profile.Picture
import com.apiguave.tinderclonedomain.profile.Gender
import com.apiguave.tinderclonedomain.profile.Orientation
import com.apiguave.tinderclonedomain.profile.RemotePicture
import com.apiguave.tinderclonedomain.profile.UserProfile
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import java.time.LocalDate

class ProfileRemoteDataSourceImpl: ProfileRemoteDataSource {

    /*
   These properties are stored due to the particularities of Firebase.
   With a traditional backend application, for queries that need the data of the current user,
   we would already have access to that data inside of the backend so there would be no need to pass it,
   however, we can't do this with Firebase restrictions, so in order to avoid the cost of performing an extra query
   we just store it in memory so that it stays available when needed.
 */
    private var currentUser: FirestoreUser? = null
    private var currentPictures: List<RemotePicture>? = null

    private suspend fun getCurrentUser(): FirestoreUser {
        return currentUser ?: run {
            val user = UserApi.getUser(AuthProvider.userId!!)!!
            currentUser = user
            user
        }
    }

    private suspend fun getCurrentPictures(): List<RemotePicture> {
        return currentPictures ?: run {
            val currentUser = getCurrentUser()
            val pictures = PictureApi.getPictures(AuthProvider.userId!!, currentUser.pictures)
            currentPictures = pictures
            pictures
        }
    }

    override suspend fun getUserProfile(): UserProfile {
        val currentUser = getCurrentUser()
        val profilePictures = getCurrentPictures()
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

    override suspend fun createProfile(
        userId: String,
        name: String,
        birthdate: LocalDate,
        bio: String,
        gender: Gender,
        orientation: Orientation,
        pictures: List<LocalPicture>
    ) {
        val filenames = PictureApi.uploadPictures(pictures.map { Uri.parse(it.uri) })
        UserApi.createUser(
            userId,
            name,
            birthdate,
            bio,
            gender,
            orientation,
            filenames.map { it.filename })
    }

    override suspend fun updateProfile(
        bio: String,
        gender: Gender,
        orientation: Orientation,
        pictures: List<Picture>
    ): UserProfile {
        val newPictures = updatePictures(pictures)
        updateUser(bio, gender, orientation, newPictures)
        return getUserProfile()
    }

    private suspend fun updateUser(
        bio: String,
        gender: Gender,
        orientation: Orientation,
        pictures: List<RemotePicture>
    ) {
        val isMale = gender.toBoolean()
        val firestoreOrientation = orientation.toFirestoreOrientation()
        val pictureNames = pictures.map { it.filename }
        UserApi.updateUser(bio, isMale, firestoreOrientation, pictureNames)
        currentUser = currentUser?.copy(bio = bio, male = isMale, orientation = firestoreOrientation, pictures = pictureNames)
    }

    private suspend fun updatePictures(pictures: List<Picture>): List<RemotePicture> {
        val outdatedPictures = getCurrentPictures()
        val updatedPictures =  coroutineScope {
            //This is a list of the pictures that were already uploaded but that have been removed from the profile.
            val picturesToDelete: List<RemotePicture> =
                outdatedPictures
                    .filter { !pictures.contains(it) }

            val pictureDeletionResult = async {
                if(picturesToDelete.isEmpty()) Unit
                else PictureApi.deletePictures(picturesToDelete.map { it.filename })
            }

            val pictureUploadResult = pictures.map {
                async {
                    when(it){
                        //If the picture was already uploaded, simply return its file name.
                        is RemotePicture -> it
                        //Otherwise uploaded and return it's new file name
                        is LocalPicture -> PictureApi.uploadPicture(Uri.parse(it.uri))
                    }
                }
            }

            pictureDeletionResult.await()
            //Returns a list of the new filenames
            pictureUploadResult.awaitAll()
        }
        currentPictures = updatedPictures
        return updatedPictures
    }

    override suspend fun getProfiles(): List<Profile> {
        val users = UserApi.getCompatibleUsers(getCurrentUser())
        return users.map { getProfile(it) }
    }

    private fun getProfile(user: FirestoreUser): Profile {
        val pictures = PictureApi.getPictures(user.id, user.pictures)
        return Profile(user.id, user.name, user.birthDate!!.toDate().toAge(), pictures)
    }

    override suspend fun passProfile(profile: Profile) {
        UserApi.swipeUser(profile.id, false)
    }

    override suspend fun likeProfile(profile: Profile): String? {
        return UserApi.swipeUser(profile.id, true)
    }

}