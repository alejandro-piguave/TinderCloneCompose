package com.apiguave.tinderclonedata.repository.auth

import com.apiguave.tinderclonedata.source.local.AuthProvider
import com.google.firebase.auth.FirebaseAuth

class AuthLocalDataSource(private val authProvider: AuthProvider) {
    val isUserSignedIn: Boolean
        get() = authProvider.isUserSignedIn

    val userId: String?
        get() = FirebaseAuth.getInstance().currentUser?.uid
}