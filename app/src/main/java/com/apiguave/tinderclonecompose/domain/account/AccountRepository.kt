package com.apiguave.tinderclonecompose.domain.account

import com.apiguave.tinderclonecompose.domain.account.entity.Account

interface AccountRepository {
    val isUserSignedIn: Boolean
    suspend fun signIn(account: Account)
    suspend fun signUp(account: Account)
    fun signOut()
}