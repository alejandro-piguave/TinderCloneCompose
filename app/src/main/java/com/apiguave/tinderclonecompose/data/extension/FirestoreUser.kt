package com.apiguave.tinderclonecompose.data.extension

import android.net.Uri
import com.apiguave.tinderclonecompose.data.datasource.model.FirestoreUser
import com.apiguave.tinderclonecompose.data.home.entity.Profile
import com.apiguave.tinderclonecompose.data.home.entity.UserProfile
import com.apiguave.tinderclonecompose.data.profile.entity.FirebasePicture
import com.apiguave.tinderclonecompose.data.profile.entity.Gender
import com.apiguave.tinderclonecompose.extensions.toAge
import com.apiguave.tinderclonecompose.extensions.toLongString

fun FirestoreUser.toProfile(uris: List<Uri>): Profile {
    return Profile(this.id, this.name, this.birthDate?.toAge() ?: 99, uris.map { it.toString() })
}

fun FirestoreUser.toUserProfile(uris: List<FirebasePicture>): UserProfile {
    return UserProfile(
        this.id,
        this.name,
        this.birthDate?.toLongString() ?: "",
        this.bio,
        this.male?.let { if(it) Gender.MALE else Gender.FEMALE } ,
        this.orientation?.toOrientation(),
        uris
    )
}