package com.apiguave.tinderclonecompose.login

import androidx.activity.result.ActivityResult
import com.apiguave.tinderclonecompose.MainDispatcherRule
import com.apiguave.tinderclonecompose.extension.toProviderAccount
import com.apiguave.tinderclonedomain.auth.Account
import com.apiguave.tinderclonedomain.usecase.IsUserSignedInUseCase
import com.apiguave.tinderclonedomain.usecase.SignInUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
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
    fun testInitializeSignedIn() {
        every { isUserSignInUseCase.invoke() } returns true
        viewModel.initialize()
        assertEquals(LoginViewState.SignedIn, viewModel.uiState.value)
    }

    @Test
    fun testInitializeSigningIn() {
        every { isUserSignInUseCase.invoke() } returns false
        viewModel.initialize()
        assertEquals(LoginViewState.SigningIn, viewModel.uiState.value)
    }

    @Test
    fun testSignInSuccess() {
        val account = Account("john.doe@gmail.com", "123456890")
        val activityResult: ActivityResult = mockk<ActivityResult>()
        mockkStatic(ActivityResult::toProviderAccount)
        coEvery { activityResult.toProviderAccount() } returns account
        coEvery { signInUseCase.invoke(any()) } returns Result.success(Unit)
        viewModel.signIn(activityResult)
        assertEquals(LoginViewState.SignedIn, viewModel.uiState.value)
    }

    @Test
    fun testSignInFailure() {
        val account = Account("john.doe@gmail.com", "123456890")
        val activityResult: ActivityResult = mockk<ActivityResult>()
        mockkStatic(ActivityResult::toProviderAccount)
        coEvery { activityResult.toProviderAccount() } returns account
        coEvery { signInUseCase.invoke(any()) } returns Result.failure(Exception())
        viewModel.signIn(activityResult)
        assertEquals(LoginViewState.Error, viewModel.uiState.value)
    }

}