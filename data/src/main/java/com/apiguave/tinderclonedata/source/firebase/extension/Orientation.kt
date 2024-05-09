package com.apiguave.tinderclonedata.source.firebase.extension

import com.apiguave.tinderclonedata.source.firebase.model.FirestoreOrientation
import com.apiguave.tinderclonedomain.profile.Orientation

fun Orientation.toFirestoreOrientation(): FirestoreOrientation = when(this) {
    Orientation.MEN -> FirestoreOrientation.men
    Orientation.WOMEN -> FirestoreOrientation.women
    Orientation.BOTH -> FirestoreOrientation.both
}