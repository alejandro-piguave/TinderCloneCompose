package com.apiguave.tinderclonedata.account.repository

interface AccountRepository {
    val isUserSignedIn: Boolean
    suspend fun signIn(account: Account)
    suspend fun signUp(account: Account)
    fun signOut()
}