package com.apiguave.tinderclonecompose.data.home.entity

import com.apiguave.tinderclonecompose.data.profile.entity.FirebasePicture
import com.apiguave.tinderclonecompose.data.profile.entity.Gender
import com.apiguave.tinderclonecompose.data.profile.entity.Orientation

data class UserProfile(val id:String="",
                       val name: String="",
                       val birthDate: String = "",
                       val bio: String = "",
                       val gender: Gender?=null,
                       val orientation: Orientation?=null,
                       val pictures: List<FirebasePicture> = emptyList())
