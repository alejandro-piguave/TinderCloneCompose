package com.apiguave.tinderclonedomain.picture

interface PictureRepository {
    suspend fun uploadPictures(userId: String, pictures: List<LocalPicture>): List<RemotePicture>
}