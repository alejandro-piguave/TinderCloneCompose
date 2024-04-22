package com.apiguave.tinderclonedata.picture

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import com.apiguave.tinderclonedata.R
import com.apiguave.tinderclonedata.extension.toBoolean
import com.apiguave.tinderclonedomain.picture.LocalPicture
import com.apiguave.tinderclonedomain.picture.PictureGenerator
import com.apiguave.tinderclonedomain.profile.Gender
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class PictureGeneratorImpl(private val context: Context): PictureGenerator {

    override suspend fun generatePictures(gender: Gender): List<LocalPicture> {
        val pictures = coroutineScope { (0 until 3).map { async { getRandomPicture(context, gender.toBoolean()) } }.awaitAll()  }
        return pictures.map { LocalPicture(it.toString()) }
    }

    private fun getRandomPicture(context: Context, isMale: Boolean): Uri {
        return context.resourceUri(if (isMale) malePictures.random() else femalePictures.random())
    }

    private fun Context.resourceUri(resourceId: Int): Uri = with(resources) {
        Uri.Builder()
            .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
            .authority(getResourcePackageName(resourceId))
            .appendPath(getResourceTypeName(resourceId))
            .appendPath(getResourceEntryName(resourceId))
            .build()
    }

    private val malePictures = listOf(
        R.drawable.man_1,
        R.drawable.man_2,
        R.drawable.man_3,
        R.drawable.man_4,
        R.drawable.man_5,
        R.drawable.man_6,
        R.drawable.man_7,
        R.drawable.man_8,
        R.drawable.man_9,
        R.drawable.man_10,
        R.drawable.man_11,
        R.drawable.man_12,
    )
    private val femalePictures = listOf(
        R.drawable.woman_1,
        R.drawable.woman_2,
        R.drawable.woman_3,
        R.drawable.woman_4,
        R.drawable.woman_5,
        R.drawable.woman_6,
        R.drawable.woman_7,
        R.drawable.woman_8,
        R.drawable.woman_9,
        R.drawable.woman_10,
        R.drawable.woman_11,
        R.drawable.woman_12,
    )
}