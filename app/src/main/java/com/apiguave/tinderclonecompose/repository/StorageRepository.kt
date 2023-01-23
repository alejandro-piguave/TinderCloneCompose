package com.apiguave.tinderclonecompose.repository

import android.graphics.Bitmap
import com.apiguave.tinderclonecompose.extensions.getTaskResult
import com.apiguave.tinderclonecompose.extensions.toByteArray
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import java.util.*

object StorageRepository {
    private const val USERS = "users"

    suspend fun uploadUserPictures(pictures: List<Bitmap>): List<String>{
        return coroutineScope {
            pictures.map { async { uploadUserPicture(it) } }.awaitAll()
        }
    }

    private suspend fun uploadUserPicture(bitmap: Bitmap): String {
        val filename = UUID.randomUUID().toString()+".jpg"
        val pictureRef = FirebaseStorage.getInstance().reference.child(USERS).child(AuthRepository.userId!!).child(filename)

        val task = pictureRef.putBytes(bitmap.toByteArray())
        task.getTaskResult()

        return filename
    }
}