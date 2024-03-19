package com.apiguave.tinderclonecompose.data.extension

import android.net.Uri
import com.apiguave.tinderclonecompose.data.home.repository.Profile
import com.apiguave.tinderclonecompose.data.user.repository.User

fun User.toProfile(uris: List<Uri>): Profile {
    return Profile(this.id, this.name, this.birthDate.toAge(), uris.map { it.toString() })
}