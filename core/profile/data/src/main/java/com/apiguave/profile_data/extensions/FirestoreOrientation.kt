package com.apiguave.profile_data.extensions

import com.apiguave.profile_domain.model.Orientation
import com.apiguave.tinderclonedata.source.firebase.model.FirestoreOrientation

fun FirestoreOrientation.toOrientation(): Orientation = when(this) {
    FirestoreOrientation.men -> Orientation.MEN
    FirestoreOrientation.women -> Orientation.WOMEN
    FirestoreOrientation.both -> Orientation.BOTH
}