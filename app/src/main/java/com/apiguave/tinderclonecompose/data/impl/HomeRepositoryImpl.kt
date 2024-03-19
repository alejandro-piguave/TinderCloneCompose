package com.apiguave.tinderclonecompose.data.impl

import com.apiguave.tinderclonecompose.data.home.datasource.HomeRemoteDataSource
import com.apiguave.tinderclonecompose.data.home.repository.HomeRepository
import com.apiguave.tinderclonecompose.data.home.repository.NewMatch
import com.apiguave.tinderclonecompose.data.home.repository.Profile
import com.apiguave.tinderclonecompose.data.user.repository.UserRepository

class HomeRepositoryImpl(
    private val userRepository: UserRepository,
    private val homeRemoteDataSource: HomeRemoteDataSource
): HomeRepository {

    override suspend fun likeProfile(profile: Profile): NewMatch? {
        val currentUser = userRepository.getCurrentUser()
        val matchModel = homeRemoteDataSource.likeProfile(currentUser.id, profile)
        return matchModel?.let { model ->
            NewMatch(model.id, profile.id, profile.name, profile.pictures)
        }
    }

    override suspend fun passProfile(profile: Profile) {
        val currentUser = userRepository.getCurrentUser()
        homeRemoteDataSource.passProfile(currentUser.id, profile)
    }

    override suspend fun getProfiles(): List<Profile> {
        val currentUser = userRepository.getCurrentUser()
        return homeRemoteDataSource.getProfiles(currentUser)
    }
}