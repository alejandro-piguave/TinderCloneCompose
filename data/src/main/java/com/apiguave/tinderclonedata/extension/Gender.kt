package com.apiguave.tinderclonedata.extension

import com.apiguave.tinderclonedata.profile.model.Gender

fun Gender.toBoolean() = when(this) {
    Gender.MALE -> true
    Gender.FEMALE -> false
}