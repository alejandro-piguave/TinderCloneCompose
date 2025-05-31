package com.apiguave.profile_data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.apiguave.profile_domain.model.Gender
import com.apiguave.profile_domain.model.Orientation
import com.apiguave.profile_domain.model.Profile
import com.apiguave.profile_domain.model.UserProfile
import com.apiguave.profile_domain.repository.ProfileRepository
import kotlinx.coroutines.flow.first
import java.time.LocalDate

val Context.dataStore by preferencesDataStore(name = "user_prefs")

class ProfileRepositoryImpl(
    private val profileRemoteDataSource: ProfileRemoteDataSource,
    private val dataStore: DataStore<Preferences>
): ProfileRepository {


    override suspend fun getProfile(): UserProfile {
        return profileRemoteDataSource.getUserProfile()
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
    }

    override suspend fun updatePictures(pictureNames: List<String>) {
        profileRemoteDataSource.updateProfile(pictureNames)
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
        return profileRemoteDataSource.getProfiles()
    }

}