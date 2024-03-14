package com.apiguave.tinderclonecompose.data.impl

import com.apiguave.tinderclonecompose.data.auth.AuthRepository
import com.apiguave.tinderclonecompose.data.user.datasource.FirestoreUserProperties
import com.apiguave.tinderclonecompose.data.extension.toBoolean
import com.apiguave.tinderclonecompose.data.extension.toFirestoreOrientation
import com.apiguave.tinderclonecompose.data.match.datasource.FirestoreMatch
import com.apiguave.tinderclonecompose.data.profile.repository.Gender
import com.apiguave.tinderclonecompose.data.profile.repository.Orientation
import com.apiguave.tinderclonecompose.data.user.datasource.UserLocalDataSource
import com.apiguave.tinderclonecompose.data.user.datasource.UserRemoteDataSource
import com.apiguave.tinderclonecompose.data.user.repository.User
import com.apiguave.tinderclonecompose.data.user.repository.UserRepository
import java.time.LocalDate

class UserRepositoryImpl(
    private val authRepository: AuthRepository,
    private val userLocalDataSource: UserLocalDataSource,
    private val userRemoteDataSource: UserRemoteDataSource): UserRepository {
    override suspend fun getCurrentUser(): User {
        userLocalDataSource.currentUser?.let {
            return it
        } ?: run {
            val currentUser = userRemoteDataSource.getUser(authRepository.userId)
            userLocalDataSource.currentUser = currentUser
            return currentUser
        }
    }

    override suspend fun updateCurrentUser(
        bio: String,
        gender: Gender,
        orientation: Orientation,
        pictures: List<String>
    ) {
        val modifiedUserData = userLocalDataSource.currentUser?.getModifiedData(bio, gender, orientation, pictures)
        if (modifiedUserData != null) {
            userRemoteDataSource.updateCurrentUser(modifiedUserData)
            userLocalDataSource.currentUser = userLocalDataSource.currentUser?.copy(bio = bio, orientation = orientation, gender = gender)
        }
    }

    private fun User.getModifiedData(bio: String, gender: Gender, orientation: Orientation, pictures: List<String>): Map<String, Any>? {
        val data = mutableMapOf<String, Any>()
        if(bio != this.bio){
            data[FirestoreUserProperties.bio] = bio
        }
        if(gender != this.gender){
            data[FirestoreUserProperties.isMale] = gender.toBoolean()
        }
        if(orientation != this.orientation){
            data[FirestoreUserProperties.orientation] = orientation.toFirestoreOrientation()
        }

        if(pictures != this.pictures) {
            data[FirestoreUserProperties.pictures] = pictures
        }

        return if(data.isEmpty()) null else data
    }


    override suspend fun getCompatibleUsers(): List<User> {
        val currentUser = getCurrentUser()
        return userRemoteDataSource.getCompatibleUsers(currentUser)
    }

    override suspend fun createUser(
        userId: String,
        name: String,
        birthdate: LocalDate,
        bio: String,
        gender: Gender,
        orientation: Orientation,
        pictures: List<String>
    ) {
        userRemoteDataSource.createUser(userId, name, birthdate, bio, gender, orientation, pictures)
    }

    override suspend fun getUser(userId: String): User {
        return userRemoteDataSource.getUser(userId)
    }

    override suspend fun likeUser(userId: String): FirestoreMatch? {
        return userRemoteDataSource.swipeUser(userId, false)
    }

    override suspend fun passUser(userId: String) {
        userRemoteDataSource.swipeUser(userId, false)
    }
}