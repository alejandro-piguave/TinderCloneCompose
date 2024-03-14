package com.apiguave.tinderclonecompose.data.impl

import com.apiguave.tinderclonecompose.data.account.AuthRemoteDataSource
import com.apiguave.tinderclonecompose.data.account.AuthRepository
import com.apiguave.tinderclonecompose.data.account.entity.Account
import com.apiguave.tinderclonecompose.data.account.exception.SignInException
import com.apiguave.tinderclonecompose.data.account.exception.SignUpException

class AuthRepositoryImpl(private val dataSource: AuthRemoteDataSource): AuthRepository {
    override val isUserSignedIn: Boolean
        get() = dataSource.isUserSignedIn

    override val userId: String
        get() = dataSource.userId

    override suspend fun signIn(account: Account) {
        val isNewAccount = dataSource.isNewAccount(account)
        if(isNewAccount) throw SignInException("User doesn't exist yet")
        else dataSource.signInWithGoogle(account)
    }

    override suspend fun signUp(account: Account) {
        val isNewAccount = dataSource.isNewAccount(account)
        if (isNewAccount) dataSource.signInWithGoogle(account)
        else throw SignUpException("User already exists")
    }

    override fun signOut(){
        dataSource.signOut()
    }
}