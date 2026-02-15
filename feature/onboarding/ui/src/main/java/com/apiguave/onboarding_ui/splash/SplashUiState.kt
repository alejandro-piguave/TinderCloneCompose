package com.apiguave.onboarding_ui.splash

sealed class SplashUiState {
    data object Loading: SplashUiState()
    data object Error: SplashUiState()
}