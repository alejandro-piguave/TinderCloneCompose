package com.apiguave.tinderclonecompose.data.datasource

import android.graphics.Bitmap
import com.apiguave.tinderclonecompose.data.profile.entity.DevicePicture
import com.apiguave.tinderclonecompose.data.profile.entity.FirebasePicture
import com.apiguave.tinderclonecompose.data.profile.entity.UserPicture
import com.apiguave.tinderclonecompose.extensions.getTaskResult
import com.apiguave.tinderclonecompose.extensions.toByteArray
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import java.util.*

class StorageRemoteDataSource {
    companion object{
        private const val USERS = "users"
    }

    suspend fun updateProfilePictures(userId: String, outdatedPictures: List<FirebasePicture>, updatedPictures: List<UserPicture>): List<FirebasePicture>{
        return coroutineScope {
            //This is a list of the pictures that were already uploaded but that have been removed from the profile.
            val picturesToDelete: List<FirebasePicture> =
                updatedPictures
                    .filter { it is FirebasePicture && !outdatedPictures.contains(it) }
                    .map { it as FirebasePicture }

            val pictureDeletionResult = async {
                if(picturesToDelete.isEmpty()) Unit
                else deleteUserPictures(userId, picturesToDelete)
            }

            val pictureUploadResult = updatedPictures.map {
                async {
                    when(it){
                        //If the picture was already uploaded, simply return its file name.
                        is FirebasePicture -> it
                        //Otherwise uploaded and return it's new file name
                        is DevicePicture -> uploadUserPicture(userId, it.bitmap)
                    }
                }
            }

            pictureDeletionResult.await()
            //Returns a list of the new filenames
            pictureUploadResult.awaitAll()
        }
    }

    private suspend fun deleteUserPictures(userId: String, pictures: List<FirebasePicture>){
        return coroutineScope {
            pictures.map { async { deleteUserPicture(userId, it) } }.awaitAll()
        }
    }

    private suspend fun deleteUserPicture(userId: String, picture: FirebasePicture){
        FirebaseStorage.getInstance().reference.child(USERS).child(userId).child(picture.filename).delete().getTaskResult()
    }

    suspend fun uploadUserPictures(userId: String, pictures: List<Bitmap>): List<FirebasePicture>{
        return coroutineScope {
            pictures.map { async { uploadUserPicture(userId, it) } }.awaitAll()
        }
    }

    private suspend fun uploadUserPicture(userId: String, bitmap: Bitmap): FirebasePicture {
        val filename = UUID.randomUUID().toString()+".jpg"
        val pictureRef = FirebaseStorage.getInstance().reference.child(USERS).child(userId).child(filename)

        pictureRef.putBytes(bitmap.toByteArray()).getTaskResult()

        return FirebasePicture(pictureRef.downloadUrl.getTaskResult(), filename)
    }

    suspend fun getPicturesFromUser(userId: String, fileNames: List<String>): List<FirebasePicture>{
        return coroutineScope {
            fileNames.map { async { getPictureFromUser(userId, it) } }.awaitAll()
        }
    }

    suspend fun getPictureFromUser(userId: String, fileName: String): FirebasePicture {
        val fileRef = FirebaseStorage.getInstance().reference.child(USERS).child(userId).child(fileName)
        return FirebasePicture(fileRef.downloadUrl.getTaskResult(), fileName)
    }
}