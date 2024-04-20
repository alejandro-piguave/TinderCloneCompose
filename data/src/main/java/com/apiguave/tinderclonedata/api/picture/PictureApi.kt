package com.apiguave.tinderclonedata.api.picture

import android.net.Uri
import com.apiguave.tinderclonecompose.data.extension.getTaskResult
import com.apiguave.tinderclonedomain.profile.RemotePicture
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import java.util.UUID

class PictureApi {
    companion object{
        private const val USERS = "users"
        private const val PROJECT_ID = "tinderclone-e07fc"
    }

    fun getPicture(userId: String, picture: String): RemotePicture {
        return RemotePicture(getPictureUrl(userId,picture), picture)
    }

    private fun getPictureUrl(userId: String, fileName: String): String {
        val storagePath = "$USERS%2F$userId%2F$fileName"
        return "https://firebasestorage.googleapis.com/v0/b/$PROJECT_ID.appspot.com/o/$storagePath?alt=media"
    }

    suspend fun getPictures(userId: String, pictures: List<String>): List<RemotePicture> {
        return coroutineScope {
            pictures.map { async { getPicture(userId, it) } }.awaitAll()
        }
    }

    suspend fun uploadPictures(userId: String, pictures: List<Uri>): List<RemotePicture>{
        return coroutineScope {
            pictures.map { async { uploadPicture(userId, it) } }.awaitAll()
        }
    }

    suspend fun uploadPicture(userId: String, picture: Uri): RemotePicture {
        val filename = UUID.randomUUID().toString()+".jpg"
        val pictureRef = FirebaseStorage.getInstance().reference.child(USERS).child(userId).child(filename)

        pictureRef.putFile(picture).getTaskResult()

        return RemotePicture(getPictureUrl(userId, filename), filename)
    }

    suspend fun deletePictures(userId: String, pictures: List<String>){
        return coroutineScope {
            pictures.map { async { deletePicture(userId, it) } }.awaitAll()
        }
    }

    private suspend fun deletePicture(userId: String, picture: String){
        FirebaseStorage.getInstance().reference.child(USERS).child(userId).child(picture).delete().getTaskResult()
    }
}