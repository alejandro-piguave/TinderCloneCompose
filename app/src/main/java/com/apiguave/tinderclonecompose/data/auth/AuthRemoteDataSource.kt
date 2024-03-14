package com.apiguave.tinderclonecompose.data.auth

import com.apiguave.tinderclonecompose.data.auth.exception.AuthException
import com.apiguave.tinderclonecompose.data.auth.entity.Account
import com.apiguave.tinderclonecompose.data.extension.getTaskResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

class AuthRemoteDataSource {

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

