package com.apiguave.tinderclonecompose.extensions

import android.net.Uri
import com.apiguave.tinderclonecompose.data.CurrentProfile
import com.apiguave.tinderclonecompose.data.FirestoreUser
import com.apiguave.tinderclonecompose.data.Profile
import com.apiguave.tinderclonecompose.data.FirebasePicture

fun FirestoreUser.toProfile(uris: List<Uri>): Profile{
    return Profile(this.id, this.name, this.birthDate?.toAge() ?: 99, uris)
}

fun FirestoreUser.toCurrentProfile(uris: List<FirebasePicture>): CurrentProfile{
    return CurrentProfile(
        this.id,
        this.name,
        this.birthDate?.toLongString() ?: "",
        this.bio,
        this.male?.let { if(it) 0 else 1 } ?: -1,
        this.orientation?.ordinal ?: -1,
        uris
    )
}