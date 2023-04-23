package com.apiguave.tinderclonecompose.data.datasource.model

data class FirestoreUserList(
    val currentUser: FirestoreUser,
    val compatibleUsers: List<FirestoreUser>)