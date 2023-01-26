package com.apiguave.tinderclonecompose.data.repository

import android.graphics.Bitmap
import com.apiguave.tinderclonecompose.data.Orientation
import java.time.LocalDate

data class CreateUserProfile(val name: String,
                             val birthdate: LocalDate,
                             val bio: String,
                             val isMale: Boolean,
                             val orientation: Orientation,
                             val pictures: List<Bitmap>
)