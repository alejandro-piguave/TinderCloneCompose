package com.apiguave.tinderclonecompose.extension

import com.apiguave.tinderclonedata.profile.repository.Gender
import com.apiguave.tinderclonedata.profile.repository.Orientation

fun Int.toGender(): Gender = when(this) {
    0 -> Gender.MALE
    1 -> Gender.FEMALE
    else -> throw IllegalArgumentException("Int must be 0 or 1 in order to covert to Gender")
}

fun Int.toOrientation(): Orientation = when(this) {
    0 -> Orientation.MEN
    1 -> Orientation.WOMEN
    2 -> Orientation.BOTH
    else -> throw IllegalArgumentException("Int must be 0 or 1 in order to covert to Gender")
}