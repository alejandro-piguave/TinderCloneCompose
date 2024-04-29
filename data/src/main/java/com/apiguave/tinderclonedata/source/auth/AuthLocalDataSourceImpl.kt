package com.apiguave.tinderclonedata.source.auth

import com.apiguave.tinderclonedata.repository.auth.AuthLocalDataSource
import com.apiguave.tinderclonedata.source.firebase.api.AuthApi

class AuthLocalDataSourceImpl: AuthLocalDataSource {
    override val userId: String?
        get() = AuthApi.userId
}