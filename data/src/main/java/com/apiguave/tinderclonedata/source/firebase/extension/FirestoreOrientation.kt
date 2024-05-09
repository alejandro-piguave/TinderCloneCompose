package com.apiguave.tinderclonedata.source.firebase.extension

import com.apiguave.tinderclonedata.source.firebase.model.FirestoreOrientation
import com.apiguave.tinderclonedomain.profile.Orientation

fun FirestoreOrientation.toOrientation(): Orientation = when(this) {
    FirestoreOrientation.men -> Orientation.MEN
    FirestoreOrientation.women -> Orientation.WOMEN
    FirestoreOrientation.both -> Orientation.BOTH
}