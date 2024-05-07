package com.apiguave.tinderclonedata.source

import com.apiguave.tinderclonedata.repository.profile.ProfileRemoteDataSource
import com.apiguave.tinderclonedata.source.extension.toAge
import com.apiguave.tinderclonedata.source.extension.toBoolean
import com.apiguave.tinderclonedata.source.extension.toFirestoreOrientation
import com.apiguave.tinderclonedata.source.extension.toLongString
import com.apiguave.tinderclonedata.source.extension.toOrientation
import com.apiguave.tinderclonedata.source.extension.toTimestamp
import com.apiguave.tinderclonedata.source.firebase.AuthApi
import com.apiguave.tinderclonedata.source.firebase.UserApi
import com.apiguave.tinderclonedata.source.firebase.model.FirestoreUser
import com.apiguave.tinderclonedomain.profile.Gender
import com.apiguave.tinderclonedomain.profile.Orientation
import com.apiguave.tinderclonedomain.profile.Profile
import com.apiguave.tinderclonedomain.profile.UserProfile
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

    private suspend fun getCurrentUser(): FirestoreUser {
        return currentUser ?: run {
            val user = UserApi.getUser(AuthApi.userId!!)!!
            currentUser = user
            user
        }
    }

    override suspend fun getUserProfile(): UserProfile {
        val currentUser = getCurrentUser()
        return UserProfile(
            currentUser.id,
            currentUser.name,
            currentUser.birthDate!!.toDate().toLongString(),
            currentUser.bio,
            if(currentUser.male!!) Gender.MALE else Gender.FEMALE,
            currentUser.orientation!!.toOrientation(),
            currentUser.pictures,
            currentUser.liked,
            currentUser.passed
        )
    }

    override suspend fun createProfile(
        userId: String,
        name: String,
        birthdate: LocalDate,
        bio: String,
        gender: Gender,
        orientation: Orientation
    ) {
        UserApi.createUser(
            userId,
            name,
            birthdate.toTimestamp(),
            bio,
            gender.toBoolean(),
            orientation.toFirestoreOrientation()
        )
    }

    override suspend fun updateProfile(
        bio: String,
        gender: Gender,
        orientation: Orientation
    ) {
        val isMale = gender.toBoolean()
        val firestoreOrientation = orientation.toFirestoreOrientation()
        UserApi.updateUser(bio, isMale, firestoreOrientation)
        currentUser = currentUser?.copy(bio = bio, male = isMale, orientation = firestoreOrientation)
    }

    override suspend fun updateProfile(pictureNames: List<String>) {
        UserApi.updateUserPictures(pictureNames)
        currentUser = currentUser?.copy(pictures = pictureNames)
    }

    override suspend fun getProfiles(): List<Profile> {
        val users = UserApi.getCompatibleUsers(getCurrentUser())
        return users.map { getProfile(it) }
    }

    private fun getProfile(user: FirestoreUser): Profile {
        return Profile(user.id, user.name, user.birthDate!!.toDate().toAge(), user.pictures)
    }

    override suspend fun passProfile(profile: Profile) {
        UserApi.swipeUser(profile.id, false)
    }

    override suspend fun likeProfile(profile: Profile): String? {
        return UserApi.swipeUser(profile.id, true)
    }

}