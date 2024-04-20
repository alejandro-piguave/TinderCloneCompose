package com.apiguave.tinderclonedata.extension

import com.apiguave.tinderclonedata.api.user.FirestoreUser
import com.apiguave.tinderclonedomain.profile.Profile

fun FirestoreUser.toProfile(uris: List<String>): Profile {
    return Profile(this.id, this.name, this.birthDate!!.toDate().toAge(), uris)
}