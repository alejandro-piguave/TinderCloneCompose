package com.apiguave.auth_data.repository

import com.apiguave.auth_domain.model.Account
import com.apiguave.auth_domain.repository.AuthRepository

class FakeAuthRepositoryImpl: AuthRepository {
    private var signedIn = true

    override val userId: String?
        get() = if(signedIn) "mock_user" else null

    override suspend fun signIn(account: Account) {
        signedIn = false
    }

    override suspend fun signUp(account: Account) {
        signedIn = true
    }

    override fun signOut() {
        signedIn = false
    }
}