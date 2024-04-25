package com.apiguave.tinderclonedata.source.api.auth

import com.google.firebase.auth.FirebaseAuth

object AuthProvider {
    val isUserSignedIn: Boolean
        get() = FirebaseAuth.getInstance().currentUser != null

    val userId: String?
        get() = FirebaseAuth.getInstance().currentUser?.uid
}