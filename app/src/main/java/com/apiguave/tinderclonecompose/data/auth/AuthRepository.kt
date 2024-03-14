package com.apiguave.tinderclonecompose.data.auth

import com.apiguave.tinderclonecompose.data.auth.entity.Account

interface AuthRepository {
    val isUserSignedIn: Boolean
    val userId: String
    suspend fun signIn(account: Account)
    suspend fun signUp(account: Account)
    fun signOut()
}