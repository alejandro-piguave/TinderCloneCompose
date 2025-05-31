package com.apiguave.profile_data.source.extensions

import com.apiguave.profile_domain.model.Gender

fun Gender.toBoolean() = when(this) {
    Gender.MALE -> true
    Gender.FEMALE -> false
}