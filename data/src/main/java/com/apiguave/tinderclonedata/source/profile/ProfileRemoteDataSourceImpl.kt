package com.apiguave.tinderclonedata.source.profile

import android.net.Uri
import com.apiguave.tinderclonedata.repository.profile.ProfileRemoteDataSource
import com.apiguave.tinderclonedata.source.api.picture.PictureApi
import com.apiguave.tinderclonedata.source.api.user.UserApi
import com.apiguave.tinderclonedata.source.extension.toBoolean
import com.apiguave.tinderclonedata.source.extension.toFirestoreOrientation
import com.apiguave.tinderclonedata.source.api.user.FirestoreUser
import com.apiguave.tinderclonedata.source.extension.toLongString
import com.apiguave.tinderclonedata.source.extension.toOrientation
import com.apiguave.tinderclonedata.source.extension.toProfile
import com.apiguave.tinderclonedomain.profile.LocalPicture
import com.apiguave.tinderclonedomain.profile.Profile
import com.apiguave.tinderclonedomain.profile.Picture
import com.apiguave.tinderclonedomain.profile.Gender
import com.apiguave.tinderclonedomain.profile.Orientation
import com.apiguave.tinderclonedomain.profile.UserProfile
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import java.time.LocalDate

class ProfileRemoteDataSourceImpl(private val userApi: UserApi, private val pictureApi: PictureApi): ProfileRemoteDataSource {
    override suspend fun getUserProfile(): UserProfile {
        val currentUser = userApi.getUser()
        val profilePictures = pictureApi.getPictures()
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
        val filenames = pictureApi.uploadPictures(pictures.map { Uri.parse(it.uri) })
        userApi.createUser(
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
        val newPictures = pictureApi.updatePictures(pictures)
        userApi.updateUser(bio, gender.toBoolean(), orientation.toFirestoreOrientation(), newPictures.map { it.filename })
        return getUserProfile()
    }

    override suspend fun getProfiles(): List<Profile> {
        val users = userApi.getCompatibleUsers()
        val profiles = coroutineScope {
            val profiles = users.map { async { getProfile(it) } }
            profiles.awaitAll()
        }
        return profiles
    }

    private suspend fun getProfile(user: FirestoreUser): Profile {
        val pictures = pictureApi.getPictures(user.id, user.pictures)
        return user.toProfile(pictures)
    }

    override suspend fun passProfile(profile: Profile) {
        userApi.swipeUser(profile.id, false)
    }

    override suspend fun likeProfile(profile: Profile): String? {
        return userApi.swipeUser(profile.id, true)
    }

}