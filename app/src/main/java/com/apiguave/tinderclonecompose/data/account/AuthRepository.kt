package com.apiguave.tinderclonecompose.data.account

import com.apiguave.tinderclonecompose.data.account.entity.Account

interface AuthRepository {
    val isUserSignedIn: Boolean
    val userId: String
    suspend fun signIn(account: Account)
    suspend fun signUp(account: Account)
    fun signOut()
}