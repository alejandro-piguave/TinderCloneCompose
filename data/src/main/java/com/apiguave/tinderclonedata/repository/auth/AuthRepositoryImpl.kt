package com.apiguave.tinderclonedata.repository.auth

import com.apiguave.domain_auth.model.Account
import com.apiguave.domain_auth.repository.AuthRepository
import com.apiguave.tinderclonedata.repository.auth.exception.SignInException
import com.apiguave.tinderclonedata.repository.auth.exception.SignUpException

class AuthRepositoryImpl(
    private val authRemoteDataSource: AuthRemoteDataSource
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