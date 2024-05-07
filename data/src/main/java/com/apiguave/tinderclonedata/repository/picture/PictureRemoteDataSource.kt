package com.apiguave.tinderclonedata.repository.picture

import android.net.Uri

interface PictureRemoteDataSource {
    suspend fun addPictures(localPictures: List<Uri>): List<String>
    suspend fun addPicture(localPicture: Uri): String
    suspend fun deletePictures(pictureNames: List<String>)
    suspend fun getPicture(userId: String, pictureName: String): Uri
}