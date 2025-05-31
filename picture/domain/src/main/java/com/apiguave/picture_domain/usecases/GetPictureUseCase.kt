package com.apiguave.picture_domain.usecases

import com.apiguave.picture_domain.repository.PictureRepository

class GetPictureUseCase(private val pictureRepository: PictureRepository) {
    suspend operator fun invoke(userId: String, pictureName: String): Result<String> {
        return Result.runCatching { pictureRepository.getPicture(userId, pictureName) }
    }
}