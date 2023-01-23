package com.apiguave.tinderclonecompose.extensions

import android.graphics.Bitmap
import java.io.ByteArrayOutputStream

fun Bitmap.toByteArray(): ByteArray{
    val stream = ByteArrayOutputStream()
    compress(Bitmap.CompressFormat.JPEG, 90, stream)
    return stream.toByteArray()
}
