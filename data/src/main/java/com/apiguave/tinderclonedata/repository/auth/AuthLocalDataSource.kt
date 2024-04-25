package com.apiguave.tinderclonedata.repository.auth

interface AuthLocalDataSource {
    val isUserSignedIn: Boolean

    val userId: String?
}