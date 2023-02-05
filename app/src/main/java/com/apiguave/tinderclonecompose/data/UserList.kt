package com.apiguave.tinderclonecompose.data

data class UserList(
    val currentUser: FirestoreUser,
    val compatibleUsers: List<FirestoreUser>)