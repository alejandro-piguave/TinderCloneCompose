package com.apiguave.data_auth.source

import com.apiguave.domain_auth.model.Account
import com.apiguave.data_auth.repository.AuthRemoteDataSource
import com.apiguave.core_firebase.AuthApi

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