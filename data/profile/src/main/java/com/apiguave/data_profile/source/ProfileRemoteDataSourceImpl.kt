package com.apiguave.data_profile.source

import com.apiguave.core_firebase.model.FirestoreUser
import java.time.LocalDate
import com.apiguave.core_firebase.AuthApi
import com.apiguave.core_firebase.UserApi
import com.apiguave.data_profile.repository.ProfileRemoteDataSource
import com.apiguave.data_profile.source.extensions.toAge
import com.apiguave.data_profile.source.extensions.toBoolean
import com.apiguave.data_profile.source.extensions.toOrientation
import com.apiguave.data_profile.source.extensions.toFirestoreOrientation
import com.apiguave.data_profile.source.extensions.toLocalDate
import com.apiguave.domain_profile.model.Gender
import com.apiguave.domain_profile.model.Orientation
import com.apiguave.domain_profile.model.Profile
import com.apiguave.domain_profile.model.UserProfile

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
            currentUser.birthDate!!.toLocalDate(),
            currentUser.bio,
            if(currentUser.male!!) Gender.MALE else Gender.FEMALE,
            currentUser.orientation!!.toOrientation(),
            currentUser.pictures
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
            birthdate,
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
        return users.map { Profile(it.id, it.name, it.birthDate!!.toAge(), it.pictures) }
    }

    override suspend fun passProfile(profile: Profile) {
        UserApi.swipeUser(profile.id, false)
    }

    override suspend fun likeProfile(profile: Profile): String? {
        return UserApi.swipeUser(profile.id, true)
    }

}