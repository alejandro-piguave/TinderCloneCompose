package com.apiguave.tinderclonedomain.account

interface AccountRepository {
    val isUserSignedIn: Boolean
    val userId: String?
    suspend fun signIn(account: Account)
    suspend fun signUp(account: Account)
    fun signOut()
}