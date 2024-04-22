package com.apiguave.tinderclonedomain.picture

import com.apiguave.tinderclonedomain.profile.Gender

interface PictureGenerator {
    suspend fun generatePictures(gender: Gender): List<LocalPicture>
}