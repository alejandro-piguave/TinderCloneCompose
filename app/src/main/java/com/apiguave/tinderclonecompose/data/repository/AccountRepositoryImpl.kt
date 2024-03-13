package com.apiguave.tinderclonecompose.data.repository

import com.apiguave.tinderclonecompose.data.datasource.AuthRemoteDataSource
import com.apiguave.tinderclonecompose.domain.account.AccountRepository
import com.apiguave.tinderclonecompose.domain.account.entity.Account
import com.apiguave.tinderclonecompose.domain.account.exception.SignInException
import com.apiguave.tinderclonecompose.domain.account.exception.SignUpException

class AccountRepositoryImpl(private val dataSource: AuthRemoteDataSource): AccountRepository {
    override val isUserSignedIn: Boolean
        get() = dataSource.isUserSignedIn

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