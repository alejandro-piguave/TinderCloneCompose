package com.apiguave.data_auth.source

import com.apiguave.domain_auth.model.Account

class AuthRemoteDataSourceMockImpl: com.apiguave.data_auth.repository.AuthRemoteDataSource {
    private var signedIn = true
    override val userId: String?
        get() = if(signedIn) "mock_user" else null

    override suspend fun isNewAccount(account: Account): Boolean = false

    override fun signOut() {
        signedIn = false
    }

    override suspend fun signInWithGoogle(account: Account) {
        signedIn = true
    }
}