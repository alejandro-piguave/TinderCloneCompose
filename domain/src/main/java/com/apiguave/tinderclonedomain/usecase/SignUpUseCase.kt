package com.apiguave.tinderclonedomain.usecase

import com.apiguave.tinderclonedomain.auth.Account
import com.apiguave.tinderclonedomain.auth.AuthRepository
import com.apiguave.tinderclonedomain.profile.Gender
import com.apiguave.tinderclonedomain.picture.PictureRepository
import com.apiguave.tinderclonedomain.profile.Orientation
import com.apiguave.tinderclonedomain.profile.ProfileRepository
import java.time.LocalDate

class SignUpUseCase(
    private val authRepository: AuthRepository,
    private val profileRepository: ProfileRepository,
    private val pictureRepository: PictureRepository
) {

    suspend operator fun invoke(
        account: Account,
        name: String,
        birthdate: LocalDate,
        bio: String,
        gender: Gender,
        orientation: Orientation,
        pictures: List<String>
    ) {
        authRepository.signUp(account)
        val userId = authRepository.userId
        profileRepository.addProfile(userId!!, name, birthdate, bio, gender, orientation)
        val pictureNames = pictureRepository.addPictures(pictures)
        profileRepository.updatePictures(pictureNames)
    }
}