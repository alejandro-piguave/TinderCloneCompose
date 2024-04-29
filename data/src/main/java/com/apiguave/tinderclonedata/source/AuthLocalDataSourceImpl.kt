package com.apiguave.tinderclonedata.source

import com.apiguave.tinderclonedata.repository.auth.AuthLocalDataSource
import com.apiguave.tinderclonedata.source.firebase.AuthApi

class AuthLocalDataSourceImpl: AuthLocalDataSource {
    override val userId: String?
        get() = AuthApi.userId
}