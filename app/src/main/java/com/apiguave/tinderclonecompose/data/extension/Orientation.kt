package com.apiguave.tinderclonecompose.data.extension

import com.apiguave.tinderclonecompose.data.datasource.model.FirestoreOrientation
import com.apiguave.tinderclonecompose.domain.profile.entity.Orientation

fun Orientation.toFirestoreOrientation(): FirestoreOrientation = when(this) {
    Orientation.MEN -> FirestoreOrientation.men
    Orientation.WOMEN -> FirestoreOrientation.women
    Orientation.BOTH -> FirestoreOrientation.both
}