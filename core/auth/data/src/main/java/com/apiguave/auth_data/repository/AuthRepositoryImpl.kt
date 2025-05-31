package com.apiguave.auth_data.repository

import com.apiguave.auth_domain.model.Account
import com.apiguave.auth_domain.repository.AuthRepository
import com.apiguave.auth_data.exception.SignInException
import com.apiguave.auth_data.exception.SignUpException

class AuthRepositoryImpl(
    private val authRemoteDataSource: AuthFirebaseDataSource
): AuthRepository {

    override val userId: String?
        get() = authRemoteDataSource.userId

    override suspend fun signIn(account: Account) {
        val isNewAccount = authRemoteDataSource.isNewAccount(account)
        if(isNewAccount) throw SignInException("User doesn't exist yet")
        else authRemoteDataSource.signInWithGoogle(account)
    }

    override suspend fun signUp(account: Account) {
        val isNewAccount = authRemoteDataSource.isNewAccount(account)
        if (isNewAccount) authRemoteDataSource.signInWithGoogle(account)
        else throw SignUpException("User already exists")
    }

    override fun signOut(){
        authRemoteDataSource.signOut()
    }
}