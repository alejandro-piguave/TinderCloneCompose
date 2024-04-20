package com.apiguave.tinderclonedomain.usecase

import com.apiguave.tinderclonedomain.account.AccountRepository

class SignOutUseCase(private val accountRepository: AccountRepository) {
    operator fun invoke(): Result<Unit> {
        return Result.runCatching {
            accountRepository.signOut()
        }
    }
}