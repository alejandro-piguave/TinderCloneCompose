package com.apiguave.tinderclonedata.source.auth

import com.apiguave.tinderclonedata.repository.auth.AuthRemoteDataSource
import com.apiguave.tinderclonedomain.auth.Account
import com.apiguave.tinderclonedata.source.firebase.api.AuthApi

class AuthRemoteDataSourceImpl: AuthRemoteDataSource {

    override suspend fun isNewAccount(account: Account): Boolean = AuthApi.isNewAccount(account.email)

    override fun signOut() {
        AuthApi.signOut()
    }

    override suspend fun signInWithGoogle(account: Account) {
        AuthApi.signInWithGoogle(account.idToken)
    }

}