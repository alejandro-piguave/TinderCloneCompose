package com.apiguave.tinderclonecompose.login

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import org.koin.androidx.compose.get
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    onNavigateToSignUp: () -> Unit,
    onNavigateToHome: () -> Unit) {
    val signInClient: GoogleSignInClient = get()
    val loginViewModel: LoginViewModel = koinViewModel()
    val uiState by loginViewModel.uiState.collectAsState()

    LaunchedEffect(key1 = uiState, block = {
        if(uiState is LoginViewState.SignedIn){
            onNavigateToHome()
        }
    })

    val startForResult = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = loginViewModel::signIn
    )
    LoginView(
        uiState = uiState,
        onNavigateToSignUp = onNavigateToSignUp,
        onSignInClicked = {
            startForResult.launch(signInClient.signInIntent)
        }
    )
}