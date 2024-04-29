package com.apiguave.tinderclonedata.source.firebase

import com.apiguave.tinderclonedata.source.firebase.exception.AuthException
import com.apiguave.tinderclonedata.source.extension.getTaskResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

object AuthApi {
    val userId: String?
        get() = FirebaseAuth.getInstance().currentUser?.uid

    suspend fun signInWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        FirebaseAuth.getInstance().signInWithCredential(credential).getTaskResult()
    }

    fun signOut(){
        FirebaseAuth.getInstance().signOut()
    }

    suspend fun isNewAccount(email: String): Boolean {
        val methods = FirebaseAuth.getInstance().fetchSignInMethodsForEmail(email).getTaskResult()
        val signInMethods = methods.signInMethods ?: throw AuthException("No sign in methods found")
        return signInMethods.isEmpty()
    }
}