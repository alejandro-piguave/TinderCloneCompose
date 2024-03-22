package com.apiguave.tinderclonedata.extension

import android.net.Uri
import com.apiguave.tinderclonedata.api.user.FirestoreUser
import com.apiguave.tinderclonedata.home.repository.Profile

fun FirestoreUser.toProfile(uris: List<Uri>): Profile {
    return Profile(this.id, this.name, this.birthDate!!.toDate().toAge(), uris.map { it.toString() })
}