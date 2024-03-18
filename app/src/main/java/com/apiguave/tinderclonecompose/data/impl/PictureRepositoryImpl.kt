package com.apiguave.tinderclonecompose.data.impl

import com.apiguave.tinderclonecompose.data.auth.AuthRepository
import com.apiguave.tinderclonecompose.data.picture.datasource.PictureLocalDataSource
import com.apiguave.tinderclonecompose.data.picture.datasource.PictureRemoteDataSource
import com.apiguave.tinderclonecompose.data.picture.repository.DevicePicture
import com.apiguave.tinderclonecompose.data.picture.repository.FirebasePicture
import com.apiguave.tinderclonecompose.data.picture.repository.Picture
import com.apiguave.tinderclonecompose.data.picture.repository.PictureRepository
import com.apiguave.tinderclonecompose.data.user.repository.User
import com.apiguave.tinderclonecompose.data.user.repository.UserRepository

class PictureRepositoryImpl(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val pictureLocalDataSource: PictureLocalDataSource,
    private val pictureRemoteDataSource: PictureRemoteDataSource): PictureRepository {
    override suspend fun getProfilePictures(): List<FirebasePicture> {
        pictureLocalDataSource.profilePictures?.let {
            return it
        } ?: run {
            val profilePictures = pictureRemoteDataSource.getPicturesFromUser(userRepository.getCurrentUser())
            pictureLocalDataSource.profilePictures = profilePictures
            return profilePictures
        }
    }

    override suspend fun updateProfilePictures(
        outdatedPictures: List<FirebasePicture>,
        updatedPictures: List<Picture>
    ): List<FirebasePicture> {
        val uploadedPictures = pictureRemoteDataSource.updateProfilePictures(authRepository.userId, outdatedPictures, updatedPictures)
        pictureLocalDataSource.profilePictures = uploadedPictures
        return uploadedPictures
    }

    override suspend fun uploadProfilePictures(
        pictures: List<DevicePicture>
    ): List<FirebasePicture> {
        val uploadedPictures = pictureRemoteDataSource.uploadUserPictures(authRepository.userId, pictures)
        pictureLocalDataSource.profilePictures = uploadedPictures
        return uploadedPictures
    }

    override suspend fun uploadPictures(
        userId: String,
        pictures: List<DevicePicture>
    ): List<FirebasePicture> {
        return pictureRemoteDataSource.uploadUserPictures(userId, pictures)
    }

    override suspend fun getPictures(user: User): List<FirebasePicture> {
        return pictureRemoteDataSource.getPicturesFromUser(user)
    }

    override suspend fun getPicture(user: User): FirebasePicture {
        return pictureRemoteDataSource.getPictureFromUser(user.id, user.pictures.first())
    }

}