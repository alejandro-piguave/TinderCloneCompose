package com.apiguave.tinderclonecompose.data.impl

import com.apiguave.tinderclonecompose.data.auth.AuthRepository
import com.apiguave.tinderclonecompose.data.picture.datasource.PictureLocalDataSource
import com.apiguave.tinderclonecompose.data.picture.datasource.PictureRemoteDataSource
import com.apiguave.tinderclonecompose.data.picture.repository.LocalPicture
import com.apiguave.tinderclonecompose.data.picture.repository.RemotePicture
import com.apiguave.tinderclonecompose.data.picture.repository.Picture
import com.apiguave.tinderclonecompose.data.picture.repository.PictureRepository
import com.apiguave.tinderclonecompose.data.user.repository.User
import com.apiguave.tinderclonecompose.data.user.repository.UserRepository

class PictureRepositoryImpl(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val pictureLocalDataSource: PictureLocalDataSource,
    private val pictureRemoteDataSource: PictureRemoteDataSource): PictureRepository {
    override suspend fun getProfilePictures(): List<RemotePicture> {
        pictureLocalDataSource.profilePictures?.let {
            return it
        } ?: run {
            val profilePictures = pictureRemoteDataSource.getPicturesFromUser(userRepository.getCurrentUser())
            pictureLocalDataSource.profilePictures = profilePictures
            return profilePictures
        }
    }

    override suspend fun updateProfilePictures(
        outdatedPictures: List<RemotePicture>,
        updatedPictures: List<Picture>
    ): List<RemotePicture> {
        val uploadedPictures = pictureRemoteDataSource.updateProfilePictures(authRepository.userId, outdatedPictures, updatedPictures)
        pictureLocalDataSource.profilePictures = uploadedPictures
        return uploadedPictures
    }

    override suspend fun uploadProfilePictures(
        pictures: List<LocalPicture>
    ): List<RemotePicture> {
        val uploadedPictures = pictureRemoteDataSource.uploadUserPictures(authRepository.userId, pictures)
        pictureLocalDataSource.profilePictures = uploadedPictures
        return uploadedPictures
    }

    override suspend fun uploadPictures(
        userId: String,
        pictures: List<LocalPicture>
    ): List<RemotePicture> {
        return pictureRemoteDataSource.uploadUserPictures(userId, pictures)
    }

    override suspend fun getPictures(user: User): List<RemotePicture> {
        return pictureRemoteDataSource.getPicturesFromUser(user)
    }

    override suspend fun getPicture(user: User): RemotePicture {
        return pictureRemoteDataSource.getPictureFromUser(user.id, user.pictures.first())
    }

}