package com.apiguave.tinderclonecompose.login

import com.apiguave.tinderclonecompose.MainDispatcherRule
import com.apiguave.tinderclonedomain.usecase.IsUserSignedInUseCase
import com.apiguave.tinderclonedomain.usecase.SignInUseCase
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test


class LoginViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val signInUseCase = mockk<SignInUseCase>()
    private val isUserSignInUseCase = mockk<IsUserSignedInUseCase>()

    private val viewModel = LoginViewModel(isUserSignInUseCase, signInUseCase)

    @Test
    fun getUiState() {
        every { isUserSignInUseCase.invoke() } returns true
        viewModel.initialize()
        assertEquals(LoginViewState.SignedIn, viewModel.uiState.value)
    }

}