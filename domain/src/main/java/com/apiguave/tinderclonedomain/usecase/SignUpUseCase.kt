package com.apiguave.tinderclonedomain.usecase

import com.apiguave.tinderclonedomain.account.Account
import com.apiguave.tinderclonedomain.account.AccountRepository
import com.apiguave.tinderclonedomain.profile.Gender
import com.apiguave.tinderclonedomain.picture.LocalPicture
import com.apiguave.tinderclonedomain.picture.PictureRepository
import com.apiguave.tinderclonedomain.profile.Orientation
import com.apiguave.tinderclonedomain.profile.ProfileRepository
import java.time.LocalDate

class SignUpUseCase(
    private val accountRepository: AccountRepository,
    private val pictureRepository: PictureRepository,
    private val profileRepository: ProfileRepository) {

    suspend operator fun invoke(
        account: Account,
        name: String,
        birthdate: LocalDate,
        bio: String,
        gender: Gender,
        orientation: Orientation,
        pictures: List<LocalPicture>
    ): Result<Unit> {
        return Result.runCatching {
            accountRepository.signUp(account)
            val userId = accountRepository.userId!!
            val remotePictures = pictureRepository.uploadPictures(userId, pictures)
            profileRepository.createProfile(userId, name, birthdate, bio, gender, orientation, remotePictures.map { it.filename })
        }
    }
}