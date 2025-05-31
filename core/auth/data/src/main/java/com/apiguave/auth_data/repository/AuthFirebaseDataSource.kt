package com.apiguave.auth_data.repository

import com.apiguave.auth_domain.model.Account
import com.apiguave.core_firebase.AuthApi

class AuthFirebaseDataSource {
    val userId: String?
        get() = AuthApi.userId

    suspend fun isNewAccount(account: Account): Boolean = AuthApi.isNewAccount(account.email)

    fun signOut() {
        AuthApi.signOut()
    }

    suspend fun signInWithGoogle(account: Account) {
        AuthApi.signInWithGoogle(account.idToken)
    }

}