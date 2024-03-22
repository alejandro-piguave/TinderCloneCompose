package com.apiguave.tinderclonedata.extension

import com.apiguave.tinderclonedata.profile.repository.Gender

fun Gender.toBoolean() = when(this) {
    Gender.MALE -> true
    Gender.FEMALE -> false
}