package com.apiguave.tinderclonecompose.data.datasource

import com.apiguave.tinderclonecompose.data.datasource.model.FirestoreUser
import com.apiguave.tinderclonecompose.domain.profile.entity.FirebasePicture

class ProfileLocalDataSource {
    var userProfile: FirestoreUser = FirestoreUser()
    var userPictures: List<FirebasePicture> = emptyList()
}