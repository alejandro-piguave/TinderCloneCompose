package com.apiguave.tinderclonedomain.usecase

import com.apiguave.tinderclonedomain.picture.LocalPicture
import com.apiguave.tinderclonedomain.picture.Picture
import com.apiguave.tinderclonedomain.picture.PictureRepository
import com.apiguave.tinderclonedomain.picture.RemotePicture
import com.apiguave.tinderclonedomain.profile.ProfileRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class UpdatePicturesUseCase(private val profileRepository: ProfileRepository, private val pictureRepository: PictureRepository) {
    suspend operator fun invoke(pictures: List<Picture>): Result<List<String>> {
        return Result.runCatching {
            val currentProfile = profileRepository.getProfile()
            val remotePictureNames = pictures.mapNotNull { if (it is RemotePicture) it.filename else null }

            //This is a list of the pictures that were already uploaded but that have been removed from the profile.
            val picturesToDelete: List<String> = currentProfile.pictureNames.filter { !remotePictureNames.contains(it) }

            coroutineScope {
                val pictureDeletionResult = async {
                    if(picturesToDelete.isEmpty()) Unit
                    else pictureRepository.deletePictures(picturesToDelete)
                }

               val pictureUploadResult = async {
                   val pictureNamesResult = pictures.map {
                       async {
                           when (it) {
                               //If the picture was already uploaded, simply return its file name.
                               is RemotePicture -> it.filename
                               //Otherwise upload it and return it's new file name
                               is LocalPicture -> pictureRepository.addPicture(it.uri)
                           }
                       }
                   }
                   val pictureNames = pictureNamesResult.awaitAll()
                   profileRepository.updatePictures(pictureNames)
                   pictureNames
               }

                pictureDeletionResult.await()
                pictureUploadResult.await()
            }

        }
    }
}