package com.apiguave.tinderclonecompose.data.repository

import android.content.Intent
import com.apiguave.tinderclonecompose.data.datasource.AuthRemoteDataSource
import com.apiguave.tinderclonecompose.data.datasource.SignInCheck
import com.apiguave.tinderclonecompose.domain.auth.AuthRepository
import com.google.firebase.auth.FirebaseUser

class AuthRepositoryImpl(private val dataSource: AuthRemoteDataSource): AuthRepository {
    override val isUserSignedIn: Boolean
        get() = dataSource.isUserSignedIn

    override val userId: String
        get() = dataSource.userId

    override suspend fun signInWithGoogle(data: Intent?, signInCheck: SignInCheck): FirebaseUser {
        return dataSource.signInWithGoogle(data, signInCheck)
    }

    override fun signOut(){
        dataSource.signOut()
    }
}