package com.apiguave.tinderclonedata.source

import com.apiguave.tinderclonedata.repository.auth.AuthRemoteDataSource
import com.apiguave.tinderclonedomain.auth.Account
import com.apiguave.tinderclonedata.source.firebase.AuthApi

class AuthRemoteDataSourceImpl: AuthRemoteDataSource {
    override val userId: String?
        get() = AuthApi.userId

    override suspend fun isNewAccount(account: Account): Boolean = AuthApi.isNewAccount(account.email)

    override fun signOut() {
        AuthApi.signOut()
    }

    override suspend fun signInWithGoogle(account: Account) {
        AuthApi.signInWithGoogle(account.idToken)
    }

}