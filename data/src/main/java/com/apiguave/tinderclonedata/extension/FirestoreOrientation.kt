package com.apiguave.tinderclonedata.extension

import com.apiguave.tinderclonedata.api.user.FirestoreOrientation
import com.apiguave.tinderclonedomain.profile.Orientation

fun FirestoreOrientation.toOrientation(): Orientation = when(this) {
    FirestoreOrientation.men -> Orientation.MEN
    FirestoreOrientation.women -> Orientation.WOMEN
    FirestoreOrientation.both -> Orientation.BOTH
}