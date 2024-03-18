package com.apiguave.tinderclonecompose.data.picture.repository

import com.apiguave.tinderclonecompose.data.user.repository.User

interface PictureRepository {
    suspend fun getProfilePictures(): List<FirebasePicture>
    suspend fun updateProfilePictures(outdatedPictures: List<FirebasePicture>, updatedPictures: List<Picture>): List<FirebasePicture>
    suspend fun uploadProfilePictures(pictures: List<DevicePicture>): List<FirebasePicture>
    suspend fun uploadPictures(userId: String, pictures: List<DevicePicture>): List<FirebasePicture>
    suspend fun getPictures(user: User): List<FirebasePicture>
    suspend fun getPicture(user: User): FirebasePicture
}