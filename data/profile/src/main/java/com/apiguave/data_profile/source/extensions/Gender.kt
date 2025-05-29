package com.apiguave.data_profile.source.extensions

import com.apiguave.tinderclonedomain.profile.Gender

fun Gender.toBoolean() = when(this) {
    Gender.MALE -> true
    Gender.FEMALE -> false
}