package com.apiguave.tinderclonecompose.data.picture.datasource

import com.apiguave.tinderclonecompose.data.picture.repository.LocalPicture
import com.apiguave.tinderclonecompose.data.picture.repository.RemotePicture
import com.apiguave.tinderclonecompose.data.picture.repository.Picture
import com.apiguave.tinderclonecompose.data.extension.getTaskResult
import com.apiguave.tinderclonecompose.data.user.repository.User
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import java.util.*

class PictureRemoteDataSource {
    companion object{
        private const val USERS = "users"
    }

    suspend fun updateProfilePictures(userId: String, outdatedPictures: List<RemotePicture>, updatedPictures: List<Picture>): List<RemotePicture>{
        return coroutineScope {
            //This is a list of the pictures that were already uploaded but that have been removed from the profile.
            val picturesToDelete: List<RemotePicture> =
                updatedPictures
                    .filter { it is RemotePicture && !outdatedPictures.contains(it) }
                    .map { it as RemotePicture }

            val pictureDeletionResult = async {
                if(picturesToDelete.isEmpty()) Unit
                else deleteUserPictures(userId, picturesToDelete)
            }

            val pictureUploadResult = updatedPictures.map {
                async {
                    when(it){
                        //If the picture was already uploaded, simply return its file name.
                        is RemotePicture -> it
                        //Otherwise uploaded and return it's new file name
                        is LocalPicture -> uploadUserPicture(userId, it)
                    }
                }
            }

            pictureDeletionResult.await()
            //Returns a list of the new filenames
            pictureUploadResult.awaitAll()
        }
    }

    private suspend fun deleteUserPictures(userId: String, pictures: List<RemotePicture>){
        return coroutineScope {
            pictures.map { async { deleteUserPicture(userId, it) } }.awaitAll()
        }
    }

    private suspend fun deleteUserPicture(userId: String, picture: RemotePicture){
        FirebaseStorage.getInstance().reference.child(USERS).child(userId).child(picture.filename).delete().getTaskResult()
    }

    suspend fun uploadUserPictures(userId: String, pictures: List<LocalPicture>): List<RemotePicture>{
        return coroutineScope {
            pictures.map { async { uploadUserPicture(userId, it) } }.awaitAll()
        }
    }

    private suspend fun uploadUserPicture(userId: String, picture: LocalPicture): RemotePicture {
        val filename = UUID.randomUUID().toString()+".jpg"
        val pictureRef = FirebaseStorage.getInstance().reference.child(USERS).child(userId).child(filename)

        pictureRef.putFile(picture.uri).getTaskResult()

        return RemotePicture(pictureRef.downloadUrl.getTaskResult(), filename)
    }

    suspend fun getPicturesFromUser(user: User): List<RemotePicture>{
        val list = FirebaseStorage.getInstance().reference.child(USERS).child(user.id).listAll().getTaskResult()
        println("For user ${user.id}:")
        list.items.forEach { println(it.name) }
        return coroutineScope {
            user.pictures.map { async { getPictureFromUser(user.id, it) } }.awaitAll()
        }
    }

    suspend fun getPictureFromUser(userId: String, picture: String): RemotePicture {
        val fileRef = Firebase.storage.reference.child("$USERS/$userId/$picture")
        return RemotePicture(fileRef.downloadUrl.getTaskResult(), picture)
    }
}