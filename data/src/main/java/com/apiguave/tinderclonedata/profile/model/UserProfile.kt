package com.apiguave.tinderclonedata.profile.model

import com.apiguave.tinderclonedata.picture.RemotePicture

data class UserProfile(val id:String="",
                       val name: String="",
                       val birthDate: String = "",
                       val bio: String = "",
                       val gender: Gender,
                       val orientation: Orientation,
                       val liked: List<String>,
                       val passed: List<String>,
                       val pictures: List<RemotePicture> = emptyList(),
)
