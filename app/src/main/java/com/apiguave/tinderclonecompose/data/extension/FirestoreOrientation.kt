package com.apiguave.tinderclonecompose.data.extension

import com.apiguave.tinderclonecompose.data.api.user.FirestoreOrientation
import com.apiguave.tinderclonecompose.data.profile.repository.Orientation

fun FirestoreOrientation.toOrientation(): Orientation = when(this) {
    FirestoreOrientation.men -> Orientation.MEN
    FirestoreOrientation.women -> Orientation.WOMEN
    FirestoreOrientation.both -> Orientation.BOTH
}