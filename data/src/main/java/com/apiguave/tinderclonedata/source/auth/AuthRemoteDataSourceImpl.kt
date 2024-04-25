package com.apiguave.tinderclonedata.source.auth

import com.apiguave.tinderclonedata.repository.auth.AuthRemoteDataSource
import com.apiguave.tinderclonedomain.auth.Account
import com.apiguave.tinderclonedata.source.api.auth.AuthApi

class AuthRemoteDataSourceImpl(private val authApi: AuthApi): AuthRemoteDataSource {

    override suspend fun isNewAccount(account: Account): Boolean = authApi.isNewAccount(account.email)

    override fun signOut() {
        authApi.signOut()
    }

    override suspend fun signInWithGoogle(account: Account) {
        authApi.signInWithGoogle(account.idToken)
    }

}