package com.apiguave.tinderclonecompose.data.picture.repository

import android.net.Uri
/*
*  A class that represents any picture given by the user.
*  Regardless of if it was already on their profile
*  or it has just been included from the device.
* */
sealed class Picture(val uri: Uri)

//A picture retrieved from the device
class DevicePicture(uri: Uri): Picture(uri)
//A picture retrieved from Firebase
class FirebasePicture(uri: Uri, val filename: String): Picture(uri)
