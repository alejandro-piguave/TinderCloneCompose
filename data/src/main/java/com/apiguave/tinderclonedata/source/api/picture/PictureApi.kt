package com.apiguave.tinderclonedata.source.api.picture

import android.net.Uri
import com.apiguave.tinderclonedata.source.api.auth.AuthProvider
import com.apiguave.tinderclonedata.source.extension.getTaskResult
import com.apiguave.tinderclonedomain.profile.RemotePicture
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import java.util.UUID

object PictureApi {
    private const val USERS = "users"
    private const val PROJECT_ID = "tinderclone-e07fc"

    private fun getPicture(userId: String, picture: String): RemotePicture {
        return RemotePicture(getPictureUrl(userId,picture), picture)
    }

    private fun getPictureUrl(userId: String, fileName: String): String {
        val storagePath = "$USERS%2F$userId%2F$fileName"
        return "https://firebasestorage.googleapis.com/v0/b/$PROJECT_ID.appspot.com/o/$storagePath?alt=media"
    }

    fun getPictures(userId: String, pictures: List<String>): List<RemotePicture> {
        return pictures.map { getPicture(userId, it) }
    }

    suspend fun uploadPictures(pictures: List<Uri>): List<RemotePicture>{
        return coroutineScope {
            pictures.map { async { uploadPicture(it) } }.awaitAll()
        }
    }

    suspend fun uploadPicture(picture: Uri): RemotePicture {
        val filename = UUID.randomUUID().toString()+".jpg"
        val pictureRef = FirebaseStorage.getInstance().reference.child(USERS).child(AuthProvider.userId!!).child(filename)

        pictureRef.putFile(picture).getTaskResult()

        return RemotePicture(getPictureUrl(AuthProvider.userId!!, filename), filename)
    }

    suspend fun deletePictures(pictures: List<String>){
        return coroutineScope {
            pictures.map { async { deletePicture(it) } }.awaitAll()
        }
    }

    private suspend fun deletePicture(picture: String){
        FirebaseStorage.getInstance().reference.child(USERS).child(AuthProvider.userId!!).child(picture).delete().getTaskResult()
    }
}