package com.apiguave.tinderclonecompose.data.repository

import android.content.Intent
import com.apiguave.tinderclonecompose.data.datasource.AuthRemoteDataSource
import com.apiguave.tinderclonecompose.data.datasource.SignInCheck
import com.google.firebase.auth.FirebaseUser

class AuthRepository(private val dataSource: AuthRemoteDataSource) {

    val isUserSignedIn: Boolean
        get() = dataSource.isUserSignedIn

    val userId: String
        get() = dataSource.userId

    suspend fun signInWithGoogle(data: Intent?, signInCheck: SignInCheck = SignInCheck.ALL_USERS): FirebaseUser {
        return dataSource.signInWithGoogle(data, signInCheck)
    }

    fun signOut(){
        dataSource.signOut()
    }
}