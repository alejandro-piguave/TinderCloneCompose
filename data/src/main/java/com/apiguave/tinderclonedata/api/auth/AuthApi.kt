package com.apiguave.tinderclonedata.api.auth

import com.apiguave.tinderclonedata.profile.repository.Account
import com.apiguave.tinderclonedata.api.auth.exception.AuthException
import com.apiguave.tinderclonecompose.data.extension.getTaskResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

class AuthApi {
    val isUserSignedIn: Boolean
        get() = FirebaseAuth.getInstance().currentUser != null

    val userId: String
        get() = FirebaseAuth.getInstance().currentUser?.uid ?: throw AuthException("User not logged in")

    suspend fun signInWithGoogle(account: Account): FirebaseUser {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        val authResult  = FirebaseAuth.getInstance().signInWithCredential(credential).getTaskResult()
        return authResult?.user ?: throw AuthException("User is empty")
    }

    fun signOut(){
        FirebaseAuth.getInstance().signOut()
    }

    suspend fun isNewAccount(account: Account): Boolean {
        val methods = FirebaseAuth.getInstance().fetchSignInMethodsForEmail(account.email).getTaskResult()
        val signInMethods = methods.signInMethods ?: throw AuthException("No sign in methods found")
        return signInMethods.isEmpty()
    }
}