package com.apiguave.tinderclonedata.profile.model

import android.net.Uri
import java.time.LocalDate

data class CreateUserProfile(val id: String,
                             val name: String,
                             val birthdate: LocalDate,
                             val bio: String,
                             val gender: Gender,
                             val orientation: Orientation,
                             val pictures: List<Uri>
)