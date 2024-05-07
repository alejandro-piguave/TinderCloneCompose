package com.apiguave.tinderclonedomain.usecase

import com.apiguave.tinderclonedomain.picture.PictureRepository

class GetPictureUseCase(private val pictureRepository: PictureRepository) {
    suspend operator fun invoke(userId: String, pictureName: String): Result<String> {
        return Result.runCatching { pictureRepository.getPicture(userId, pictureName) }
    }
}