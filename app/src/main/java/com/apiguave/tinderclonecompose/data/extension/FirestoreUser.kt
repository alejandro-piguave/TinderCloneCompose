package com.apiguave.tinderclonecompose.data.extension

import com.apiguave.tinderclonecompose.data.api.user.FirestoreUser
import com.apiguave.tinderclonecompose.data.profile.repository.Gender
import com.apiguave.tinderclonecompose.data.user.repository.User

fun FirestoreUser.toUser(): User {
    return User(id, name, birthDate!!.toDate(), bio, if(male!!) Gender.MALE else Gender.FEMALE, orientation!!.toOrientation(), liked, passed, pictures)
}