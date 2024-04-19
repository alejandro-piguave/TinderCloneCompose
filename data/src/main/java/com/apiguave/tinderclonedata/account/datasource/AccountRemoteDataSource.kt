package com.apiguave.tinderclonedata.account.datasource

import com.apiguave.tinderclonedata.account.repository.Account
import com.apiguave.tinderclonedata.api.auth.AuthApi

class AccountRemoteDataSource(private val authApi: AuthApi) {
    val isUserSignedIn: Boolean
        get() = authApi.isUserSignedIn

    val userId: String
        get() = authApi.userId

    suspend fun isNewAccount(account: Account): Boolean = authApi.isNewAccount(account.email)

    fun signOut() {
        authApi.signOut()
    }

    suspend fun signInWithGoogle(account: Account) {
        authApi.signInWithGoogle(account.idToken)
    }

}