package com.apiguave.tinderclonecompose.data.picture.repository

import com.apiguave.tinderclonecompose.data.user.repository.User

interface PictureRepository {
    suspend fun getProfilePictures(): List<RemotePicture>
    suspend fun updateProfilePictures(outdatedPictures: List<RemotePicture>, updatedPictures: List<Picture>): List<RemotePicture>
    suspend fun uploadProfilePictures(pictures: List<LocalPicture>): List<RemotePicture>
    suspend fun uploadPictures(userId: String, pictures: List<LocalPicture>): List<RemotePicture>
    suspend fun getPictures(user: User): List<RemotePicture>
    suspend fun getPicture(user: User): RemotePicture
}