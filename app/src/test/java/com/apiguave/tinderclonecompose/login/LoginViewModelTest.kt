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
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class LoginViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val signInUseCase = mockk<SignInUseCase>()
    private val isUserSignInUseCase = mockk<IsUserSignedInUseCase> {
        every { this@mockk.invoke() } returns true
    }

    private lateinit var viewModel: LoginViewModel

    @Before
    fun initialize() {
        viewModel = LoginViewModel(isUserSignInUseCase, signInUseCase)

        val account = Account("john.doe@gmail.com", "123456890")
        mockkStatic(ActivityResult::toProviderAccount)
        coEvery { any<ActivityResult>().toProviderAccount() } returns account
    }

    @Test
    fun testInitializeSignedIn() {
        assertEquals(LoginViewState.SignedIn, viewModel.uiState.value)
    }

    @Test
    fun testInitializeSigningIn() {
        val isUserSignedInUseCase = mockk<IsUserSignedInUseCase> {
            every { this@mockk.invoke() } returns false
        }
        val userNotSignedInViewModel = LoginViewModel(isUserSignedInUseCase, signInUseCase)
        assertEquals(LoginViewState.SigningIn, userNotSignedInViewModel.uiState.value)
    }

    @Test
    fun testSignInSuccess() {
        val activityResult: ActivityResult = mockk<ActivityResult>()
        coEvery { signInUseCase.invoke(any()) } returns Result.success(Unit)
        viewModel.signIn(activityResult)
        assertEquals(LoginViewState.SignedIn, viewModel.uiState.value)
    }

    @Test
    fun testSignInFailure() {
        val activityResult: ActivityResult = mockk<ActivityResult>()
        coEvery { signInUseCase.invoke(any()) } returns Result.failure(Exception())
        viewModel.signIn(activityResult)
        assertEquals(LoginViewState.Error, viewModel.uiState.value)
    }

}