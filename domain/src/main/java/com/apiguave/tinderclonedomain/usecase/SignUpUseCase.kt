package com.apiguave.tinderclonedomain.usecase

import com.apiguave.tinderclonedomain.account.Account
import com.apiguave.tinderclonedomain.account.AccountRepository
import com.apiguave.tinderclonedomain.profile.Gender
import com.apiguave.tinderclonedomain.profile.LocalPicture
import com.apiguave.tinderclonedomain.profile.Orientation
import com.apiguave.tinderclonedomain.profile.ProfileRepository
import java.time.LocalDate

class SignUpUseCase(
    private val accountRepository: AccountRepository,
    private val profileRepository: ProfileRepository) {

    suspend operator fun invoke(
        account: Account,
        name: String,
        birthdate: LocalDate,
        bio: String,
        gender: Gender,
        orientation: Orientation,
        pictures: List<LocalPicture>
    ) {
        accountRepository.signUp(account)
        val userId = accountRepository.userId
        profileRepository.createProfile(userId!!, name, birthdate, bio, gender, orientation, pictures)
    }
}