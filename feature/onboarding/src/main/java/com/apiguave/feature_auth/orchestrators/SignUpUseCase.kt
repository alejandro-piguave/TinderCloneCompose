package com.apiguave.feature_auth.orchestrators

import com.apiguave.domain_auth.model.Account
import com.apiguave.domain_auth.repository.AuthRepository
import com.apiguave.domain_picture.repository.PictureRepository
import com.apiguave.domain_profile.model.Gender
import com.apiguave.domain_profile.model.Orientation
import com.apiguave.domain_profile.repository.ProfileRepository
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
    ): Result<Unit> {
        return Result.runCatching {
            authRepository.signUp(account)
            val userId = authRepository.userId
            profileRepository.addProfile(userId!!, name, birthdate, bio, gender, orientation)
            val pictureNames = pictureRepository.addPictures(pictures)
            profileRepository.updatePictures(pictureNames)
        }
    }
}
