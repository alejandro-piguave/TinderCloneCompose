package com.apiguave.profile_data.repository

import com.apiguave.core_firebase.UserApi
import com.apiguave.core_firebase.model.FirestoreUser
import com.apiguave.profile_data.extensions.toAge
import com.apiguave.profile_data.extensions.toBoolean
import com.apiguave.profile_data.extensions.toFirestoreOrientation
import com.apiguave.profile_domain.model.Gender
import com.apiguave.profile_domain.model.Orientation
import com.apiguave.profile_domain.model.Profile
import java.time.LocalDate

class ProfileFirebaseDataSource {

    suspend fun createProfile(
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

    suspend fun updateProfile(
        bio: String,
        gender: Gender,
        orientation: Orientation
    ) {
        val isMale = gender.toBoolean()
        val firestoreOrientation = orientation.toFirestoreOrientation()
        UserApi.updateUser(bio, isMale, firestoreOrientation)
    }

    suspend fun updateProfile(pictureNames: List<String>) {
        UserApi.updateUserPictures(pictureNames)
    }

    suspend fun getProfiles(user: FirestoreUser): List<Profile> {
        val users = UserApi.getCompatibleUsers(user)
        return users.map { Profile(it.id, it.name, it.birthDate!!.toAge(), it.pictures) }
    }

    suspend fun hasUserProfile(): Boolean {
        return UserApi.userExists()
    }

    suspend fun passProfile(profile: Profile) {
        UserApi.swipeUser(profile.id, false)
    }

    suspend fun likeProfile(profile: Profile): String? {
        return UserApi.swipeUser(profile.id, true)
    }

}