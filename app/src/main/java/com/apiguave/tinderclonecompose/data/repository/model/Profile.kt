package com.apiguave.tinderclonecompose.data.repository.model

import android.net.Uri

class Profile(
    val id: String,
    val name: String,
    val age: Int,
    val pictures: List<Uri>,
)