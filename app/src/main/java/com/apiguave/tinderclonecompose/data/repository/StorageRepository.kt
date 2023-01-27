package com.apiguave.tinderclonecompose.data.repository

import android.graphics.Bitmap
import android.net.Uri
import com.apiguave.tinderclonecompose.extensions.getTaskResult
import com.apiguave.tinderclonecompose.extensions.toByteArray
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.*
import java.util.*

class StorageRepository {
    companion object{
        private const val USERS = "users"
    }

    suspend fun uploadUserPictures(userId: String, pictures: List<Bitmap>): List<String>{
        return coroutineScope {
            pictures.map { async { uploadUserPicture(userId, it) } }.awaitAll()
        }
    }

    private suspend fun uploadUserPicture(userId: String, bitmap: Bitmap): String {
        val filename = UUID.randomUUID().toString()+".jpg"
        val pictureRef = FirebaseStorage.getInstance().reference.child(USERS).child(userId).child(filename)

        val task = pictureRef.putBytes(bitmap.toByteArray())
        task.getTaskResult()

        return filename
    }

    suspend fun getUrisFromUser(userId: String, fileNames: List<String>): List<Uri>{
        return coroutineScope {
            fileNames.map { async { getUriFromUser(userId, it) } }.awaitAll()
        }
    }

    suspend fun getUriFromUser(userId: String, fileName: String): Uri {
        val fileRef = FirebaseStorage.getInstance().reference.child(USERS).child(userId).child(fileName)
        return fileRef.downloadUrl.getTaskResult()
    }
}