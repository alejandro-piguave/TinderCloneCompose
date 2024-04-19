package com.apiguave.tinderclonecompose.data.extension

import com.apiguave.tinderclonedata.api.user.FirestoreOrientation
import com.apiguave.tinderclonedata.profile.model.Orientation

fun Orientation.toFirestoreOrientation(): FirestoreOrientation = when(this) {
    Orientation.MEN -> FirestoreOrientation.men
    Orientation.WOMEN -> FirestoreOrientation.women
    Orientation.BOTH -> FirestoreOrientation.both
}