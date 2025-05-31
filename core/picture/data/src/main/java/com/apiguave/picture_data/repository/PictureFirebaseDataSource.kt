package com.apiguave.picture_data.repository

import android.net.Uri
import com.apiguave.core_firebase.PictureApi

class PictureFirebaseDataSource {

    suspend fun addPictures(localPictures: List<Uri>): List<String> {
        return PictureApi.addPictures(localPictures)
    }

    suspend fun addPicture(localPicture: Uri): String {
        return PictureApi.addPicture(localPicture)
    }

    suspend fun deletePictures(pictureNames: List<String>) {
        PictureApi.deletePictures(pictureNames)
    }

    suspend fun getPicture(userId: String, pictureName: String): Uri =
        PictureApi.getPictureUrl(userId, pictureName)
}