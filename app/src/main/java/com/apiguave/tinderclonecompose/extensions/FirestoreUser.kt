package com.apiguave.tinderclonecompose.extensions

import android.net.Uri
import com.apiguave.tinderclonecompose.data.CurrentProfile
import com.apiguave.tinderclonecompose.data.FirestoreUser
import com.apiguave.tinderclonecompose.data.Profile

fun FirestoreUser.toProfile(uris: List<Uri>): Profile{
    return Profile(this.id, this.name, this.birthDate?.toAge() ?: 99, uris)
}

fun FirestoreUser.toCurrentProfile(uris: List<Uri>): CurrentProfile{
    return CurrentProfile(
        this.id,
        this.name,
        this.birthDate?.toLongString() ?: throw IllegalStateException("birthDate field can't be null"),
        this.bio,
        this.male ?: false,
        this.orientation ?: throw IllegalStateException("'orientation' field can't be null"),
        uris
    )
}