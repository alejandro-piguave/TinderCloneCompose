package com.apiguave.tinderclonecompose.data.api.picture

import com.apiguave.tinderclonecompose.data.extension.getTaskResult
import com.apiguave.tinderclonecompose.data.picture.repository.RemotePicture
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class PictureApi {
    companion object{
        private const val USERS = "users"
    }

    suspend fun getPicture(userId: String, picture: String): RemotePicture {
        val fileRef = Firebase.storage.reference.child("$USERS/$userId/$picture")
        return RemotePicture(fileRef.downloadUrl.getTaskResult(), picture)
    }
}