package com.apiguave.onboarding_ui.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.apiguave.onboarding_domain.StartDestination
import org.koin.androidx.compose.koinViewModel

@Composable
fun SplashScreen(
    onNavigateToCreateProfile: () -> Unit,
    onNavigateToSignIn: () -> Unit,
    onNavigateToHome: () -> Unit) {
    val viewModel: SplashViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.eventFlow.collect {
                when(it) {
                    StartDestination.SignIn -> onNavigateToSignIn()
                    StartDestination.CreateProfile -> onNavigateToCreateProfile()
                    StartDestination.Home -> onNavigateToHome()
                }
            }
         }
    }

    SplashView(state, onTryAgainClick = { viewModel.resolveStartDestination() })
}