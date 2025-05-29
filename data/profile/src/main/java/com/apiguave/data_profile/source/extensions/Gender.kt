package com.apiguave.data_profile.source.extensions

import com.apiguave.domain_profile.model.Gender

fun Gender.toBoolean() = when(this) {
    Gender.MALE -> true
    Gender.FEMALE -> false
}