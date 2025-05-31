package com.apiguave.profile_data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.apiguave.core_firebase.AuthApi
import com.apiguave.core_firebase.UserApi
import com.apiguave.core_firebase.model.FirestoreUser
import com.apiguave.profile_data.extensions.toBoolean
import com.apiguave.profile_data.extensions.toFirestoreOrientation
import com.apiguave.profile_data.extensions.toLocalDate
import com.apiguave.profile_data.extensions.toOrientation
import com.apiguave.profile_domain.model.Gender
import com.apiguave.profile_domain.model.Orientation
import com.apiguave.profile_domain.model.Profile
import com.apiguave.profile_domain.model.UserProfile
import com.apiguave.profile_domain.repository.ProfileRepository
import kotlinx.coroutines.flow.first
import java.time.LocalDate

val Context.dataStore by preferencesDataStore(name = "user_prefs")

class ProfileRepositoryImpl(
    private val profileRemoteDataSource: ProfileFirebaseDataSource,
    private val dataStore: DataStore<Preferences>
): ProfileRepository {

    private var currentUser: FirestoreUser? = null

    override suspend fun getProfile(): UserProfile {
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

    private suspend fun getCurrentUser(): FirestoreUser {
        return currentUser ?: run {
            val user = UserApi.getUser(AuthApi.userId!!)!!
            currentUser = user
            user
        }
    }

    override suspend fun hasProfile(userId: String): Boolean {
        val preferences = dataStore.data.first()
        val hasProfileKey = booleanPreferencesKey("HAS_PROFILE_$userId")
        return preferences[hasProfileKey] ?: run {
            val hasProfile = profileRemoteDataSource.hasUserProfile()
            if(hasProfile){
                dataStore.edit { preferences ->
                    preferences[hasProfileKey] = true
                }
            }
            return hasProfile
        }
    }

    override suspend fun updateProfile(
        bio: String,
        gender: Gender,
        orientation: Orientation
    ) {
        profileRemoteDataSource.updateProfile(bio, gender, orientation)
        currentUser = currentUser?.copy(bio = bio, male = gender.toBoolean(), orientation = orientation.toFirestoreOrientation())
    }

    override suspend fun updatePictures(pictureNames: List<String>) {
        profileRemoteDataSource.updateProfile(pictureNames)
        currentUser = currentUser?.copy(pictures = pictureNames)
    }

    override suspend fun addProfile(
        userId: String,
        name: String,
        birthdate: LocalDate,
        bio: String,
        gender: Gender,
        orientation: Orientation
    ) {
        profileRemoteDataSource.createProfile(userId, name, birthdate, bio, gender, orientation)
    }

    override suspend fun likeProfile(profile: Profile): String? {
        return profileRemoteDataSource.likeProfile(profile)
    }

    override suspend fun passProfile(profile: Profile) {
        profileRemoteDataSource.passProfile(profile)
    }

    override suspend fun getProfiles(): List<Profile> {
        return profileRemoteDataSource.getProfiles(getCurrentUser())
    }

}