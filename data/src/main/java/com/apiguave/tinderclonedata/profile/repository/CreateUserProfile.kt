package com.apiguave.tinderclonedata.profile.repository

import com.apiguave.tinderclonedata.picture.LocalPicture
import java.time.LocalDate

data class CreateUserProfile(val name: String,
                             val birthdate: LocalDate,
                             val bio: String,
                             val gender: Gender,
                             val orientation: Orientation,
                             val pictures: List<LocalPicture>
)