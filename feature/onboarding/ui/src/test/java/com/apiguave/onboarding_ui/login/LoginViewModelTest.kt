package com.apiguave.onboarding_ui.login

import androidx.activity.result.ActivityResult
import com.apiguave.onboarding_ui.extensions.toProviderAccount
import com.apiguave.auth_domain.model.Account
import com.apiguave.auth_domain.usecases.SignInUseCase
import com.apiguave.onboarding_ui.MainDispatcherRule
import io.mockk.coEvery
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
    private lateinit var signedInViewModel: LoginViewModel
    private lateinit var notSignedInViewModel: LoginViewModel


    @Before
    fun initialize() {
        signedInViewModel = LoginViewModel(signInUseCase)
        notSignedInViewModel = LoginViewModel(signInUseCase)
        val account = Account("john.doe@gmail.com", "123456890")
        mockkStatic(ActivityResult::toProviderAccount)
        coEvery { any<ActivityResult>().toProviderAccount() } returns account
    }

    @Test
    fun testInitializeSignedIn() {
        assertEquals(LoginViewState.Ready, signedInViewModel.uiState.value)
    }

    @Test
    fun testInitializeSigningIn() {
        assertEquals(LoginViewState.Ready, notSignedInViewModel.uiState.value)
    }

    @Test
    fun testSignInSuccess() {
        val activityResult: ActivityResult = mockk<ActivityResult>()
        coEvery { signInUseCase.invoke(any()) } returns Result.success(Unit)
        signedInViewModel.signIn(activityResult)
        assertEquals(LoginViewState.Loading, signedInViewModel.uiState.value)
    }

    @Test
    fun testSignInFailure() {
        val activityResult: ActivityResult = mockk<ActivityResult>()
        coEvery { signInUseCase.invoke(any()) } returns Result.failure(Exception())
        signedInViewModel.signIn(activityResult)
        assertEquals(LoginViewState.Ready, signedInViewModel.uiState.value)
    }

}