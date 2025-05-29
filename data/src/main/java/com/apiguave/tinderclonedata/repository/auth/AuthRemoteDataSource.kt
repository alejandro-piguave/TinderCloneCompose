package com.apiguave.tinderclonedata.repository.auth

import com.apiguave.domain_auth.model.Account


interface AuthRemoteDataSource {
    val userId: String?
    suspend fun isNewAccount(account: Account): Boolean
    fun signOut()
    suspend fun signInWithGoogle(account: Account)
}