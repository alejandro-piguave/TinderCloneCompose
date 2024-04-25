package com.apiguave.tinderclonedata.source.api.picture

import android.net.Uri
import com.apiguave.tinderclonedata.source.api.auth.AuthProvider
import com.apiguave.tinderclonedata.source.api.user.UserApi
import com.apiguave.tinderclonedata.source.extension.getTaskResult
import com.apiguave.tinderclonedomain.profile.LocalPicture
import com.apiguave.tinderclonedomain.profile.Picture
import com.apiguave.tinderclonedomain.profile.RemotePicture
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import java.util.UUID

class PictureApi(private val authProvider: AuthProvider, private val userApi: UserApi) {
    companion object {
        private const val USERS = "users"
        private const val PROJECT_ID = "tinderclone-e07fc"
    }

    /*
       This property is stored due to the particularities of Firebase.
       With a traditional backend application, for queries that need the data of the current user,
       we would already have access to that data inside of the backend so there would be no need to pass it,
       however, we can't do this with Firebase restrictions, so in order to avoid the cost of performing an extra query
       we just store it in memory so that it stays available when needed.
     */
    private var userPictures: List<RemotePicture>? = null

    private fun getPicture(userId: String, picture: String): RemotePicture {
        return RemotePicture(getPictureUrl(userId,picture), picture)
    }

    private fun getPictureUrl(userId: String, fileName: String): String {
        val storagePath = "$USERS%2F$userId%2F$fileName"
        return "https://firebasestorage.googleapis.com/v0/b/$PROJECT_ID.appspot.com/o/$storagePath?alt=media"
    }

    //Get the pictures of the signed in user
    suspend fun getPictures(): List<RemotePicture> {
        val user = userApi.getUser()
        return userPictures ?: getPictures(user.id, user.pictures)
    }

    suspend fun getPictures(userId: String, pictures: List<String>): List<RemotePicture> {
        return coroutineScope {
            pictures.map { async { getPicture(userId, it) } }.awaitAll()
        }
    }

    suspend fun uploadPictures(pictures: List<Uri>): List<RemotePicture>{
        return coroutineScope {
            pictures.map { async { uploadPicture(it) } }.awaitAll()
        }
    }

    private suspend fun uploadPicture(picture: Uri): RemotePicture {
        val filename = UUID.randomUUID().toString()+".jpg"
        val pictureRef = FirebaseStorage.getInstance().reference.child(USERS).child(authProvider.userId!!).child(filename)

        pictureRef.putFile(picture).getTaskResult()

        return RemotePicture(getPictureUrl(authProvider.userId!!, filename), filename)
    }

    private suspend fun deletePictures(pictures: List<String>){
        return coroutineScope {
            pictures.map { async { deletePicture(it) } }.awaitAll()
        }
    }

    private suspend fun deletePicture(picture: String){
        FirebaseStorage.getInstance().reference.child(USERS).child(authProvider.userId!!).child(picture).delete().getTaskResult()
    }

    suspend fun updatePictures(pictures: List<Picture>): List<RemotePicture> {
        val outdatedPictures = getPictures()
        val updatedPictures =  coroutineScope {
            //This is a list of the pictures that were already uploaded but that have been removed from the profile.
            val picturesToDelete: List<RemotePicture> =
                outdatedPictures
                    .filter { !pictures.contains(it) }

            val pictureDeletionResult = async {
                if(picturesToDelete.isEmpty()) Unit
                else deletePictures(picturesToDelete.map { it.filename })
            }

            val pictureUploadResult = pictures.map {
                async {
                    when(it){
                        //If the picture was already uploaded, simply return its file name.
                        is RemotePicture -> it
                        //Otherwise uploaded and return it's new file name
                        is LocalPicture -> uploadPicture(Uri.parse(it.uri))
                    }
                }
            }

            pictureDeletionResult.await()
            //Returns a list of the new filenames
            pictureUploadResult.awaitAll()
        }
        userPictures = updatedPictures
        return updatedPictures
    }
}