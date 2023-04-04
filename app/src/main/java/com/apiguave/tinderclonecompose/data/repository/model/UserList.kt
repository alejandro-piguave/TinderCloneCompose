package com.apiguave.tinderclonecompose.data.repository.model

import com.apiguave.tinderclonecompose.data.datasource.model.FirestoreUser

data class UserList(
    val currentUser: FirestoreUser,
    val compatibleUsers: List<FirestoreUser>)