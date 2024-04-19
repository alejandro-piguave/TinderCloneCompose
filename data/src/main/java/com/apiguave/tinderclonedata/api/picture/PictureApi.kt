package com.apiguave.tinderclonedata.api.picture

import android.net.Uri
import com.apiguave.tinderclonecompose.data.extension.getTaskResult
import com.apiguave.tinderclonedata.picture.RemotePicture
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

        return RemotePicture(pictureRef.downloadUrl.getTaskResult(), filename)
    }

    suspend fun deletePictures(userId: String, pictures: List<RemotePicture>){
        return coroutineScope {
            pictures.map { async { deletePicture(userId, it.filename) } }.awaitAll()
        }
    }

    private suspend fun deletePicture(userId: String, picture: String){
        FirebaseStorage.getInstance().reference.child(USERS).child(userId).child(picture).delete().getTaskResult()
    }
}