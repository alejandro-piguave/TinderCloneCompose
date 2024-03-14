package com.apiguave.tinderclonecompose.data.user.repository

import com.apiguave.tinderclonecompose.data.profile.repository.Gender
import com.apiguave.tinderclonecompose.data.profile.repository.Orientation

interface UserRepository {
    suspend fun getCurrentUser(): User
    suspend fun updateCurrentUser(bio: String, gender: Gender, orientation: Orientation, pictures: List<String>)
    suspend fun getCompatibleUsers(): List<User>
}