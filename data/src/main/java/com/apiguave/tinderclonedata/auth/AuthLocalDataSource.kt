package com.apiguave.tinderclonedata.auth

import com.google.firebase.auth.FirebaseAuth

class AuthLocalDataSource {
    val isUserSignedIn: Boolean
        get() = FirebaseAuth.getInstance().currentUser != null

    val userId: String?
        get() = FirebaseAuth.getInstance().currentUser?.uid
}