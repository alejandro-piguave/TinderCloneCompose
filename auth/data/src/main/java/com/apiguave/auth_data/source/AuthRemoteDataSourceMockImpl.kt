package com.apiguave.auth_data.source

import com.apiguave.auth_domain.model.Account

class AuthRemoteDataSourceMockImpl: com.apiguave.auth_data.repository.AuthRemoteDataSource {
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