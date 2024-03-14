package com.apiguave.tinderclonecompose.data.profile.repository

import android.graphics.Bitmap
import java.time.LocalDate

data class CreateUserProfile(val name: String,
                             val birthdate: LocalDate,
                             val bio: String,
                             val gender: Gender,
                             val orientation: Orientation,
                             val pictures: List<Bitmap>
)