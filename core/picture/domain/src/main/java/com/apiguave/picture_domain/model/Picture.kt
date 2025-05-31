package com.apiguave.picture_domain.model

/*
*  A class that represents any picture given by the user.
*  Regardless of if it was already on their profile
*  or it has just been included from the device.
* */
sealed class Picture(val uri: String)

//A picture retrieved from the device
class LocalPicture(uri: String): Picture(uri)
//A picture retrieved from Firebase
class RemotePicture(uri: String, val filename: String): Picture(uri)
