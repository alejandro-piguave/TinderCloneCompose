package com.apiguave.tinderclonedomain.usecase

import com.apiguave.tinderclonedomain.account.AccountRepository

class IsUserSignedInUseCase(private val accountRepository: AccountRepository) {

    operator fun invoke(): Boolean = accountRepository.isUserSignedIn
}