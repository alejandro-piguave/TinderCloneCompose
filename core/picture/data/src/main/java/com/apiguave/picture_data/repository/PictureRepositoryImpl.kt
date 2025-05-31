package com.apiguave.picture_data.repository

import android.net.Uri
import com.apiguave.picture_domain.repository.PictureRepository

class PictureRepositoryImpl(private val pictureRemoteDataSource: PictureFirebaseDataSource):
    PictureRepository {
    override suspend fun addPictures(localPictures: List<String>): List<String> {
        return pictureRemoteDataSource.addPictures(localPictures.map { Uri.parse(it) })
    }

    override suspend fun addPicture(localPicture: String): String {
        return pictureRemoteDataSource.addPicture(Uri.parse(localPicture))
    }

    override suspend fun deletePictures(pictureNames: List<String>) {
        pictureRemoteDataSource.deletePictures(pictureNames)
    }

    override suspend fun getPicture(userId: String, pictureName: String): String {
        return pictureRemoteDataSource.getPicture(userId, pictureName).toString()
    }

}