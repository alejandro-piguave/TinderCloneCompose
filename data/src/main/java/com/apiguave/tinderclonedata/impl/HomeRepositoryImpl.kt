package com.apiguave.tinderclonedata.impl

import android.content.Context
import com.apiguave.tinderclonedata.extension.getRandomProfile
import com.apiguave.tinderclonedata.extension.getRandomUserId
import com.apiguave.tinderclonedata.home.datasource.HomeRemoteDataSource
import com.apiguave.tinderclonedata.home.repository.HomeRepository
import com.apiguave.tinderclonedata.home.repository.NewMatch
import com.apiguave.tinderclonedata.home.repository.Profile
import com.apiguave.tinderclonedata.profile.repository.ProfileRepository

class HomeRepositoryImpl(
    private val context: Context,
    private val profileRepository: ProfileRepository,
    private val homeRemoteDataSource: HomeRemoteDataSource
): HomeRepository {

    override suspend fun likeProfile(profile: Profile): NewMatch? {
        val currentUser = profileRepository.getProfile()
        val matchModel = homeRemoteDataSource.likeProfile(currentUser.id, profile)
        return matchModel?.let { model ->
            NewMatch(model.id, profile.id, profile.name, profile.pictures)
        }
    }

    override suspend fun passProfile(profile: Profile) {
        val currentUser = profileRepository.getProfile()
        homeRemoteDataSource.passProfile(currentUser.id, profile)
    }

    override suspend fun getProfiles(): List<Profile> {
        val currentUser = profileRepository.getProfile()
        return homeRemoteDataSource.getProfiles(currentUser)
    }

    private suspend fun createRandomProfile() {
        val userId = getRandomUserId()
        val profile = getRandomProfile(context)
        homeRemoteDataSource.createProfile(userId, profile)
    }

    override suspend fun createRandomProfiles(amount: Int) {
        //Profiles are not created concurrently to avoid memory overhead when loading the images
        for(i in 0 until amount) {
            createRandomProfile()
        }
    }
}