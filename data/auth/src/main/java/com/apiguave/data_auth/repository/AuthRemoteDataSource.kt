package com.apiguave.data_auth.repository

import com.apiguave.domain_auth.model.Account


interface AuthRemoteDataSource {
    val userId: String?
    suspend fun isNewAccount(account: Account): Boolean
    fun signOut()
    suspend fun signInWithGoogle(account: Account)
}