package com.apiguave.tinderclonecompose.data.user.repository

import com.apiguave.tinderclonecompose.data.profile.repository.Gender
import com.apiguave.tinderclonecompose.data.profile.repository.Orientation
import java.time.LocalDate

interface UserRepository {
    suspend fun getCurrentUser(): User
    suspend fun updateCurrentUser(bio: String, gender: Gender, orientation: Orientation, pictures: List<String>)
    suspend fun getCompatibleUsers(): List<User>
    suspend fun createUser(
        userId: String,
        name: String,
        birthdate: LocalDate,
        bio: String,
        gender: Gender,
        orientation: Orientation,
        pictures: List<String>)
    suspend fun getUser(userId: String): User
}