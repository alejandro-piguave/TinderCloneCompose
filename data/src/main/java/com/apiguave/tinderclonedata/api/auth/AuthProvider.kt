package com.apiguave.tinderclonedata.api.auth

import com.google.firebase.auth.FirebaseAuth

class AuthProvider {
    val isUserSignedIn: Boolean
        get() = FirebaseAuth.getInstance().currentUser != null

    val userId: String?
        get() = FirebaseAuth.getInstance().currentUser?.uid
}