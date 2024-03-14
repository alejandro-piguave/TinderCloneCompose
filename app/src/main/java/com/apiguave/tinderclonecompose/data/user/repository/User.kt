package com.apiguave.tinderclonecompose.data.user.repository

import com.apiguave.tinderclonecompose.data.profile.repository.Gender
import com.apiguave.tinderclonecompose.data.profile.repository.Orientation
import java.util.Date

data class User(val id:String="",
                val name: String="",
                val birthDate: Date,
                val bio: String = "",
                val gender: Gender,
                val orientation: Orientation,
                val liked: List<String>,
                val passed: List<String>,
                val pictures: List<String> = emptyList(),
)