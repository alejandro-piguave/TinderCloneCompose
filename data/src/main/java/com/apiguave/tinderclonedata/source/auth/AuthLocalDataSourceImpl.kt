package com.apiguave.tinderclonedata.source.auth

import com.apiguave.tinderclonedata.repository.auth.AuthLocalDataSource
import com.apiguave.tinderclonedata.source.api.auth.AuthProvider

class AuthLocalDataSourceImpl: AuthLocalDataSource {
    override val isUserSignedIn: Boolean
        get() = AuthProvider.isUserSignedIn

    override val userId: String?
        get() = AuthProvider.userId
}