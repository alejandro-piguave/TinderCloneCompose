package com.apiguave.tinderclonecompose.extensions

import android.net.Uri
import com.apiguave.tinderclonecompose.data.datasource.model.FirestoreUser
import com.apiguave.tinderclonecompose.data.extension.toOrientation
import com.apiguave.tinderclonecompose.domain.home.entity.Profile
import com.apiguave.tinderclonecompose.domain.home.entity.UserProfile
import com.apiguave.tinderclonecompose.domain.profile.entity.FirebasePicture
import com.apiguave.tinderclonecompose.domain.profile.entity.Gender

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