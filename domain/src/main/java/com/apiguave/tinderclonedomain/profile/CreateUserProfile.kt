package com.apiguave.tinderclonedomain.profile

import java.time.LocalDate

data class CreateUserProfile(val id: String,
                             val name: String,
                             val birthdate: LocalDate,
                             val bio: String,
                             val gender: Gender,
                             val orientation: Orientation,
                             val pictures: List<LocalPicture>
)