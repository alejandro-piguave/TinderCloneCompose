package com.apiguave.tinderclonecompose.data.extension

import android.net.Uri
import com.apiguave.tinderclonecompose.data.home.entity.Profile
import com.apiguave.tinderclonecompose.data.picture.repository.FirebasePicture
import com.apiguave.tinderclonecompose.data.profile.repository.UserProfile
import com.apiguave.tinderclonecompose.data.user.repository.User

fun User.toProfile(uris: List<Uri>): Profile {
    return Profile(this.id, this.name, this.birthDate.toAge(), uris.map { it.toString() })
}

fun User.toUserProfile(uris: List<FirebasePicture>): UserProfile {
    return UserProfile(this.id, this.name, this.birthDate.toLongString(), this.bio, this.gender, this.orientation, uris)
}