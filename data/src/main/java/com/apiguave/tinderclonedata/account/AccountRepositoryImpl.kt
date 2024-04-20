package com.apiguave.tinderclonedata.account

import com.apiguave.tinderclonedomain.account.Account
import com.apiguave.tinderclonedomain.account.AccountRepository
import com.apiguave.tinderclonedata.api.auth.exception.SignInException
import com.apiguave.tinderclonedata.api.auth.exception.SignUpException

class AccountRepositoryImpl(private val accountRemoteDataSource: AccountRemoteDataSource):
    AccountRepository {

    override val isUserSignedIn: Boolean
        get() = accountRemoteDataSource.isUserSignedIn

    override val userId: String
        get() = accountRemoteDataSource.userId

    override suspend fun signIn(account: Account) {
        val isNewAccount = accountRemoteDataSource.isNewAccount(account)
        if(isNewAccount) throw SignInException("User doesn't exist yet")
        else accountRemoteDataSource.signInWithGoogle(account)
    }

    override suspend fun signUp(account: Account) {
        val isNewAccount = accountRemoteDataSource.isNewAccount(account)
        if (isNewAccount) accountRemoteDataSource.signInWithGoogle(account)
        else throw SignUpException("User already exists")
    }

    override fun signOut(){
        accountRemoteDataSource.signOut()
    }
}