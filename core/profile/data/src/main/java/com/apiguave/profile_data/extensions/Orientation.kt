package com.apiguave.profile_data.extensions

import com.apiguave.profile_domain.model.Orientation
import com.apiguave.tinderclonedata.source.firebase.model.FirestoreOrientation

fun Orientation.toFirestoreOrientation(): FirestoreOrientation = when(this) {
    Orientation.MEN -> FirestoreOrientation.men
    Orientation.WOMEN -> FirestoreOrientation.women
    Orientation.BOTH -> FirestoreOrientation.both
}