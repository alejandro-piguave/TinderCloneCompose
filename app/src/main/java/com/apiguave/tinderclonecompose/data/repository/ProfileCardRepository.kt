package com.apiguave.tinderclonecompose.data.repository

import com.apiguave.tinderclonecompose.data.repository.model.NewMatch
import com.apiguave.tinderclonecompose.data.repository.model.Profile
import com.apiguave.tinderclonecompose.data.repository.model.ProfileList

interface ProfileCardRepository {
    suspend fun swipeUser(profile: Profile, isLike: Boolean): NewMatch?
    suspend fun getProfiles(): ProfileList
}