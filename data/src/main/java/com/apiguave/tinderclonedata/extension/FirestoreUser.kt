package com.apiguave.tinderclonedata.extension

import com.apiguave.tinderclonedata.api.user.FirestoreUser
import com.apiguave.tinderclonedomain.profile.Profile
import com.apiguave.tinderclonedomain.profile.RemotePicture

fun FirestoreUser.toProfile(uris: List<RemotePicture>): Profile {
    return Profile(this.id, this.name, this.birthDate!!.toDate().toAge(), uris)
}