package com.apiguave.tinderclonecompose.data.api.picture

import com.apiguave.tinderclonecompose.data.extension.getTaskResult
import com.apiguave.tinderclonecompose.data.picture.repository.LocalPicture
import com.apiguave.tinderclonecompose.data.picture.repository.RemotePicture
import com.apiguave.tinderclonecompose.data.user.repository.User
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import java.util.UUID

class PictureApi {
    companion object{
        private const val USERS = "users"
    }

    suspend fun getPicture(userId: String, picture: String): RemotePicture {
        val fileRef = Firebase.storage.reference.child("$USERS/$userId/$picture")
        return RemotePicture(fileRef.downloadUrl.getTaskResult(), picture)
    }

    suspend fun getPictures(user: User): List<RemotePicture> {
        return coroutineScope {
            user.pictures.map { async { getPicture(user.id, it) } }.awaitAll()
        }
    }

    suspend fun uploadPictures(userId: String, pictures: List<LocalPicture>): List<RemotePicture>{
        return coroutineScope {
            pictures.map { async { uploadPicture(userId, it) } }.awaitAll()
        }
    }

    private suspend fun uploadPicture(userId: String, picture: LocalPicture): RemotePicture {
        val filename = UUID.randomUUID().toString()+".jpg"
        val pictureRef = FirebaseStorage.getInstance().reference.child(USERS).child(userId).child(filename)

        pictureRef.putFile(picture.uri).getTaskResult()

        return RemotePicture(pictureRef.downloadUrl.getTaskResult(), filename)
    }
}