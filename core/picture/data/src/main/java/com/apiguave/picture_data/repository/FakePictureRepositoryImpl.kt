package com.apiguave.picture_data.repository

import android.content.Context
import com.apiguave.picture_data.R
import com.apiguave.picture_data.extensions.resourceUri
import com.apiguave.picture_domain.repository.PictureRepository
import kotlinx.coroutines.delay
import kotlin.random.Random

class FakePictureRepositoryImpl(private val context: Context): PictureRepository {
    override suspend fun addPictures(localPictures: List<String>): List<String> {
        delay(1000)
        return localPictures.mapIndexed { _, i ->
            "picture$i.jpg"
        }
    }

    override suspend fun addPicture(localPicture: String): String {
        delay(1000)
        return "picture1.jpg"
    }

    override suspend fun deletePictures(pictureNames: List<String>) {
        delay(500)
    }

    override suspend fun getPicture(userId: String, pictureName: String): String {
        //The random delayed is used to showcase the picture asynchronous loading
        delay(Random.nextLong(1000, 4000))
        return when(pictureName){
            "man_1.jpg" -> context.resourceUri(R.drawable.man_1)
            "man_2.jpg" -> context.resourceUri(R.drawable.man_2)
            "man_3.jpg" -> context.resourceUri(R.drawable.man_3)
            "man_4.jpg" -> context.resourceUri(R.drawable.man_4)
            "man_5.jpg" -> context.resourceUri(R.drawable.man_5)
            "man_6.jpg" -> context.resourceUri(R.drawable.man_6)
            "man_7.jpg" -> context.resourceUri(R.drawable.man_7)
            "man_8.jpg" -> context.resourceUri(R.drawable.man_8)
            "man_9.jpg" -> context.resourceUri(R.drawable.man_9)
            "man_10.jpg" -> context.resourceUri(R.drawable.man_10)
            "man_11.jpg" -> context.resourceUri(R.drawable.man_11)
            "man_12.jpg" -> context.resourceUri(R.drawable.man_11)
            "woman_1.jpg" -> context.resourceUri(R.drawable.woman_1)
            "woman_2.jpg" -> context.resourceUri(R.drawable.woman_2)
            "woman_3.jpg" -> context.resourceUri(R.drawable.woman_3)
            "woman_4.jpg" -> context.resourceUri(R.drawable.woman_4)
            "woman_5.jpg" -> context.resourceUri(R.drawable.woman_5)
            "woman_6.jpg" -> context.resourceUri(R.drawable.woman_6)
            "woman_7.jpg" -> context.resourceUri(R.drawable.woman_7)
            "woman_8.jpg" -> context.resourceUri(R.drawable.woman_8)
            "woman_9.jpg" -> context.resourceUri(R.drawable.woman_9)
            "woman_10.jpg" -> context.resourceUri(R.drawable.woman_10)
            "woman_11.jpg" -> context.resourceUri(R.drawable.woman_11)
            "woman_12.jpg" -> context.resourceUri(R.drawable.woman_12)
            else -> context.resourceUri(R.drawable.man_1)
        }.toString()
    }
}