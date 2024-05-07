package com.apiguave.tinderclonedata.repository.picture

import android.net.Uri
import com.apiguave.tinderclonedomain.picture.PictureRepository

class PictureRepositoryImpl(private val pictureRemoteDataSource: PictureRemoteDataSource): PictureRepository {
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