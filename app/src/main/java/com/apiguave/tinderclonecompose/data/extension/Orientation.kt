package com.apiguave.tinderclonecompose.data.extension

import com.apiguave.tinderclonecompose.data.api.user.FirestoreOrientation
import com.apiguave.tinderclonecompose.data.profile.repository.Orientation

fun Orientation.toFirestoreOrientation(): FirestoreOrientation = when(this) {
    Orientation.MEN -> FirestoreOrientation.men
    Orientation.WOMEN -> FirestoreOrientation.women
    Orientation.BOTH -> FirestoreOrientation.both
}