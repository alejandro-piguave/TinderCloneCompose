package com.apiguave.picture_data.source

import android.net.Uri
import com.apiguave.core_firebase.PictureApi
import com.apiguave.picture_data.repository.PictureRemoteDataSource

class PictureRemoteDataSourceImpl: PictureRemoteDataSource {

    override suspend fun addPictures(localPictures: List<Uri>): List<String> {
        return PictureApi.addPictures(localPictures)
    }

    override suspend fun addPicture(localPicture: Uri): String {
        return PictureApi.addPicture(localPicture)
    }

    override suspend fun deletePictures(pictureNames: List<String>) {
        PictureApi.deletePictures(pictureNames)
    }

    override suspend fun getPicture(userId: String, pictureName: String): Uri =
        PictureApi.getPictureUrl(userId, pictureName)
}