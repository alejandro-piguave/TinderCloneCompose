package com.apiguave.tinderclonedata.source.firebase

import android.net.Uri
import com.apiguave.tinderclonedata.repository.picture.PictureRemoteDataSource

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