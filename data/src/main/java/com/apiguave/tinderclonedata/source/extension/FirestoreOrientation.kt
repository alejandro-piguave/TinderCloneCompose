package com.apiguave.tinderclonedata.source.extension

import com.apiguave.tinderclonedata.source.api.user.FirestoreOrientation
import com.apiguave.tinderclonedomain.profile.Orientation

fun FirestoreOrientation.toOrientation(): Orientation = when(this) {
    FirestoreOrientation.men -> Orientation.MEN
    FirestoreOrientation.women -> Orientation.WOMEN
    FirestoreOrientation.both -> Orientation.BOTH
}