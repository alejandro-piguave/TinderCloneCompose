package com.apiguave.tinderclonecompose.data

import android.graphics.Bitmap
import android.net.Uri
/*
*  A class that represents any picture given by the user.
*  Regardless of if it was already on their profile
*  or it has just been included from the device.
* */
sealed class UserPicture(val uri: Uri)

//A picture retrieved from the device
class DevicePicture(uri: Uri, val bitmap: Bitmap): UserPicture(uri)
//A picture retrieved from Firebase
class FirebasePicture(uri: Uri, val filename: String): UserPicture(uri)
