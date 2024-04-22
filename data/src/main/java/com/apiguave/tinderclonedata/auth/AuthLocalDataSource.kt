package com.apiguave.tinderclonedata.auth

import com.apiguave.tinderclonedata.api.auth.AuthProvider
import com.google.firebase.auth.FirebaseAuth

class AuthLocalDataSource(private val authProvider: AuthProvider) {
    val isUserSignedIn: Boolean
        get() = authProvider.isUserSignedIn

    val userId: String?
        get() = FirebaseAuth.getInstance().currentUser?.uid
}