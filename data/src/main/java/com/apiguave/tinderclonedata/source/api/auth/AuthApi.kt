package com.apiguave.tinderclonedata.source.api.auth

import com.apiguave.tinderclonedata.source.api.auth.exception.AuthException
import com.apiguave.tinderclonedata.source.extension.getTaskResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

object AuthApi {

    suspend fun signInWithGoogle(idToken: String): FirebaseUser {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        val authResult  = FirebaseAuth.getInstance().signInWithCredential(credential).getTaskResult()
        return authResult?.user ?: throw AuthException("User is empty")
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