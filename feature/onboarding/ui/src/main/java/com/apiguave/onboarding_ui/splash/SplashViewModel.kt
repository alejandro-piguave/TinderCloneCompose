package com.apiguave.onboarding_ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apiguave.onboarding_domain.ResolveStartDestinationUseCase
import com.apiguave.onboarding_domain.StartDestination
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SplashViewModel(
    private val resolveStartDestinationUseCase: ResolveStartDestinationUseCase
): ViewModel() {

    private val _event = Channel<StartDestination>()
    val eventFlow = _event.receiveAsFlow()

    private val _state = MutableStateFlow<SplashViewState>(SplashViewState.Loading)
    val state = _state.asStateFlow()

    init {
        resolveStartDestination()
    }

    fun resolveStartDestination() {
        _state.update { SplashViewState.Loading }
        viewModelScope.launch {
            resolveStartDestinationUseCase().onSuccess { result ->
                _event.send(result)
            }.onFailure {
                _state.update { SplashViewState.Error }
            }
        }
    }
}

sealed class SplashViewState {
    data object Loading: SplashViewState()
    data object Error: SplashViewState()
}