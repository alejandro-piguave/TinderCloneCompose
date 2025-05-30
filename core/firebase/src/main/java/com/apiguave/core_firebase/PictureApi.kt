package com.apiguave.core_firebase

import android.net.Uri
import com.apiguave.core_firebase.extensions.getTaskResult
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import java.util.UUID

object PictureApi {
    private const val USERS = "users"

    suspend fun getPictureUrl(userId: String, fileName: String): Uri  =
        FirebaseStorage.getInstance().reference.child(USERS)
            .child(userId).child(fileName).downloadUrl.getTaskResult()

    suspend fun addPictures(pictures: List<Uri>): List<String>{
        return coroutineScope {
            pictures.map { async { addPicture(it) } }.awaitAll()
        }
    }

    suspend fun addPicture(picture: Uri): String {
        val filename = UUID.randomUUID().toString()+".jpg"
        val pictureRef = FirebaseStorage.getInstance().reference.child(USERS).child(AuthApi.userId!!).child(filename)

        pictureRef.putFile(picture).getTaskResult()

        return filename
    }

    suspend fun  deletePictures(pictures: List<String>){
        return coroutineScope {
            pictures.map { async { deletePicture(it) } }.awaitAll()
        }
    }

    private suspend fun deletePicture(picture: String){
        FirebaseStorage.getInstance().reference.child(USERS).child(AuthApi.userId!!).child(picture).delete().getTaskResult()
    }
}