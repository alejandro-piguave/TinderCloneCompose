package com.apiguave.profile_domain.model

import java.time.LocalDate

data class UserProfile(val id:String="",
                       val name: String="",
                       val birthDate: LocalDate,
                       val bio: String = "",
                       val gender: Gender,
                       val orientation: Orientation,
                       val pictureNames: List<String>)
