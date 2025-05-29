package com.apiguave.domain_auth.repository

import com.apiguave.domain_auth.model.Account

interface AuthRepository {
    val userId: String?
    suspend fun signIn(account: Account)
    suspend fun signUp(account: Account)
    fun signOut()
}