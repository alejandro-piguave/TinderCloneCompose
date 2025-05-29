package com.apiguave.auth_ui.extensions

import androidx.activity.result.ActivityResult
import com.apiguave.domain_auth.model.Account
import com.google.android.gms.auth.api.signin.GoogleSignIn

suspend fun ActivityResult.toProviderAccount(): Account {
    val task = GoogleSignIn.getSignedInAccountFromIntent(data)
    val account = task.getTaskResult()
    return Account(account.email!!, account.idToken!!)
}