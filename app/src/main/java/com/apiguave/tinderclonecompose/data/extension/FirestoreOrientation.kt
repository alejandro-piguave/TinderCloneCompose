package com.apiguave.tinderclonecompose.data.extension

import com.apiguave.tinderclonecompose.data.datasource.model.FirestoreOrientation
import com.apiguave.tinderclonecompose.data.profile.entity.Orientation

fun FirestoreOrientation.toOrientation(): Orientation = when(this) {
    FirestoreOrientation.men -> Orientation.MEN
    FirestoreOrientation.women -> Orientation.WOMEN
    FirestoreOrientation.both -> Orientation.BOTH
}