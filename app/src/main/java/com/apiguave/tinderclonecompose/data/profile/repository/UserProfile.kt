package com.apiguave.tinderclonecompose.data.profile.repository

import com.apiguave.tinderclonecompose.data.picture.repository.RemotePicture

data class UserProfile(val id:String="",
                       val name: String="",
                       val birthDate: String = "",
                       val bio: String = "",
                       val gender: Gender,
                       val orientation: Orientation,
                       val pictures: List<RemotePicture> = emptyList(),
)
