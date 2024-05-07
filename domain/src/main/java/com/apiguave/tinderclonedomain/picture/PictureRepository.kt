package com.apiguave.tinderclonedomain.picture

interface PictureRepository {
    suspend fun addPictures(localPictures: List<String>): List<String>
    suspend fun addPicture(localPicture: String): String
    suspend fun deletePictures(pictureNames: List<String>)
    suspend fun getPicture(userId: String, pictureName: String): String
}