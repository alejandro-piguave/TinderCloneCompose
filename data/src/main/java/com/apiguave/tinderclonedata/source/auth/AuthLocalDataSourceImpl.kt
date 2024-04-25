package com.apiguave.tinderclonedata.source.auth

import com.apiguave.tinderclonedata.repository.auth.AuthLocalDataSource
import com.apiguave.tinderclonedata.source.local.AuthProvider

class AuthLocalDataSourceImpl(private val authProvider: AuthProvider): AuthLocalDataSource {
    override val isUserSignedIn: Boolean
        get() = authProvider.isUserSignedIn

    override val userId: String?
        get() = authProvider.userId
}