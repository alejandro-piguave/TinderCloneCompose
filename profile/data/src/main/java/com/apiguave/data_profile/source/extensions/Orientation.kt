package com.apiguave.data_profile.source.extensions

import com.apiguave.domain_profile.model.Orientation
import com.apiguave.tinderclonedata.source.firebase.model.FirestoreOrientation

fun Orientation.toFirestoreOrientation(): FirestoreOrientation = when(this) {
    Orientation.MEN -> FirestoreOrientation.men
    Orientation.WOMEN -> FirestoreOrientation.women
    Orientation.BOTH -> FirestoreOrientation.both
}