package com.apiguave.tinderclonecompose.data

import android.net.Uri

data class CurrentProfile(
    val id: String,
    val name: String,
    val birthDate: String,
    val bio: String,
    val isMale: Boolean,
    val orientation: Orientation,
    val pictures: List<Uri>
)