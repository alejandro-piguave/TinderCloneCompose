package com.apiguave.tinderclonecompose.data.account

import com.apiguave.tinderclonecompose.data.account.entity.Account

interface AccountRepository {
    val isUserSignedIn: Boolean
    suspend fun signIn(account: Account)
    suspend fun signUp(account: Account)
    fun signOut()
}