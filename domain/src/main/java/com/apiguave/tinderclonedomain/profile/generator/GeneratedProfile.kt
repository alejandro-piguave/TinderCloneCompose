package com.apiguave.tinderclonedomain.profile.generator

import com.apiguave.tinderclonedomain.profile.Gender
import com.apiguave.tinderclonedomain.profile.LocalPicture
import com.apiguave.tinderclonedomain.profile.Orientation
import java.time.LocalDate

data class GeneratedProfile(val id: String,
                            val name: String,
                            val birthdate: LocalDate,
                            val bio: String,
                            val gender: Gender,
                            val orientation: Orientation,
                            val pictures: List<LocalPicture>
)