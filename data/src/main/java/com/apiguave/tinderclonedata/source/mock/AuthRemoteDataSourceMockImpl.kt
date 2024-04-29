package com.apiguave.tinderclonedata.source.mock

import com.apiguave.tinderclonedata.repository.auth.AuthRemoteDataSource
import com.apiguave.tinderclonedomain.auth.Account

class AuthRemoteDataSourceMockImpl: AuthRemoteDataSource {
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