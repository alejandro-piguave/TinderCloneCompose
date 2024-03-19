package com.apiguave.tinderclonecompose.data.extension

import android.net.Uri
import com.apiguave.tinderclonecompose.data.api.user.FirestoreUser
import com.apiguave.tinderclonecompose.data.home.repository.Profile

fun FirestoreUser.toProfile(uris: List<Uri>): Profile {
    return Profile(this.id, this.name, this.birthDate!!.toDate().toAge(), uris.map { it.toString() })
}