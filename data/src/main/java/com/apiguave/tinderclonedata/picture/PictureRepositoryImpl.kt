package com.apiguave.tinderclonedata.picture

import com.apiguave.tinderclonedomain.picture.LocalPicture
import com.apiguave.tinderclonedomain.picture.PictureRepository
import com.apiguave.tinderclonedomain.picture.RemotePicture

class PictureRepositoryImpl(private val remoteDataSource: PictureRemoteDataSource): PictureRepository {
    override suspend fun uploadPictures(
        userId: String,
        pictures: List<LocalPicture>
    ): List<RemotePicture> {
        return remoteDataSource.uploadPictures(userId, pictures)
    }
}