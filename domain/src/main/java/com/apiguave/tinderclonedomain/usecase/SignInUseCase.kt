package com.apiguave.tinderclonedomain.usecase

import com.apiguave.tinderclonedomain.account.Account
import com.apiguave.tinderclonedomain.account.AccountRepository

class SignInUseCase(private val accountRepository: AccountRepository) {

    suspend operator fun invoke(account: Account): Result<Unit> {
        return try {
            accountRepository.signIn(account)
            Result.success(Unit)
        } catch (e: Throwable) {
            if(accountRepository.isUserSignedIn){
                accountRepository.signOut()
            }
            Result.failure(e)
        }
    }
}