package com.apiguave.auth_domain.repository

import com.apiguave.auth_domain.model.Account

interface AuthRepository {
    val userId: String?
    suspend fun signIn(account: Account)
    suspend fun signUp(account: Account)
    fun signOut()
}