package com.apiguave.tinderclonedata.picture

import android.net.Uri
import com.apiguave.tinderclonedata.api.picture.PictureApi
import com.apiguave.tinderclonedomain.picture.LocalPicture
import com.apiguave.tinderclonedomain.picture.RemotePicture

class PictureRemoteDataSource(private val pictureApi: PictureApi) {
    suspend fun uploadPictures(userId: String, pictures: List<LocalPicture>): List<RemotePicture> {
        return pictureApi.uploadPictures(userId, pictures.map { Uri.parse(it.uri) })
    }
}