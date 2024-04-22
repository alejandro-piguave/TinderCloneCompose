package com.apiguave.tinderclonedomain.usecase

import com.apiguave.tinderclonedomain.picture.PictureGenerator
import com.apiguave.tinderclonedomain.picture.PictureRepository
import com.apiguave.tinderclonedomain.profile.CreateUserProfile
import com.apiguave.tinderclonedomain.profile.ProfileGenerator
import com.apiguave.tinderclonedomain.profile.ProfileRepository

class GenerateProfilesUseCase(
    private val profileGenerator: ProfileGenerator,
    private val pictureGenerator: PictureGenerator,
    private val profileRepository: ProfileRepository,
    private val pictureRepository: PictureRepository
    ) {

    suspend operator fun invoke(amount: Int): Result<Unit> {
        return Result.runCatching {
            (0 until amount).forEach { _ ->
                val profile = profileGenerator.generateProfile()
                val pictures = pictureGenerator.generatePictures(profile.gender)
                val remotePictures = pictureRepository.uploadPictures(profile.id, pictures)
                val createProfile = CreateUserProfile(profile.id, profile.name, profile.birthdate, profile.bio, profile.gender, profile.orientation, remotePictures.map { it.filename })
                profileRepository.createProfile(createProfile)
            }
        }
    }
}