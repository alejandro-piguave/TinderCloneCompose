package com.apiguave.auth_data.repository

import com.apiguave.auth_domain.model.Account


interface AuthRemoteDataSource {
    val userId: String?
    suspend fun isNewAccount(account: Account): Boolean
    fun signOut()
    suspend fun signInWithGoogle(account: Account)
}