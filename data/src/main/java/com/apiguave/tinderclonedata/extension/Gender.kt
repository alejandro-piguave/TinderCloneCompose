package com.apiguave.tinderclonedata.extension

import com.apiguave.tinderclonedomain.profile.Gender

fun Gender.toBoolean() = when(this) {
    Gender.MALE -> true
    Gender.FEMALE -> false
}