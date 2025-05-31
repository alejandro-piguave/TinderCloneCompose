package com.apiguave.domain_picture.usecases

import com.apiguave.domain_picture.repository.PictureRepository

class GetPictureUseCase(private val pictureRepository: PictureRepository) {
    suspend operator fun invoke(userId: String, pictureName: String): Result<String> {
        return Result.runCatching { pictureRepository.getPicture(userId, pictureName) }
    }
}