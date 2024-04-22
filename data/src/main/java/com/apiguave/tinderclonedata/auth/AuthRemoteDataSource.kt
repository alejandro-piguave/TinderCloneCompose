package com.apiguave.tinderclonedata.auth

import com.apiguave.tinderclonedomain.auth.Account
import com.apiguave.tinderclonedata.api.auth.AuthApi

class AuthRemoteDataSource(private val authApi: AuthApi) {

    suspend fun isNewAccount(account: Account): Boolean = authApi.isNewAccount(account.email)

    fun signOut() {
        authApi.signOut()
    }

    suspend fun signInWithGoogle(account: Account) {
        authApi.signInWithGoogle(account.idToken)
    }

}