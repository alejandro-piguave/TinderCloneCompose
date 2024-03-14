package com.apiguave.tinderclonecompose.data.datasource.model

data class FirestoreHomeData(
    val currentUser: FirestoreUser,
    val compatibleUsers: List<FirestoreUser>)