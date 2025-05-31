package com.apiguave.data_profile.source.extensions

import com.apiguave.domain_profile.model.Orientation
import com.apiguave.tinderclonedata.source.firebase.model.FirestoreOrientation

fun FirestoreOrientation.toOrientation(): Orientation = when(this) {
    FirestoreOrientation.men -> Orientation.MEN
    FirestoreOrientation.women -> Orientation.WOMEN
    FirestoreOrientation.both -> Orientation.BOTH
}