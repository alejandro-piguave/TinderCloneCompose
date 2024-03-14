package com.apiguave.tinderclonecompose.data.extension

import com.apiguave.tinderclonecompose.data.profile.entity.Gender

fun Gender.toBoolean() = when(this) {
    Gender.MALE -> true
    Gender.FEMALE -> false
}