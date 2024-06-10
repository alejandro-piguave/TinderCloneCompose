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
    private val userSignedInUseCase = mockk<IsUserSignedInUseCase> {
        every { this@mockk.invoke() } returns true
    }
    private val userNotSignedInUseCase = mockk<IsUserSignedInUseCase> {
        every { this@mockk.invoke() } returns false
    }

    private lateinit var signedInViewModel: LoginViewModel
    private lateinit var notSignedInViewModel: LoginViewModel


    @Before
    fun initialize() {
        signedInViewModel = LoginViewModel(userSignedInUseCase, signInUseCase)
        notSignedInViewModel = LoginViewModel(userNotSignedInUseCase, signInUseCase)
        val account = Account("john.doe@gmail.com", "123456890")
        mockkStatic(ActivityResult::toProviderAccount)
        coEvery { any<ActivityResult>().toProviderAccount() } returns account
    }

    @Test
    fun testInitializeSignedIn() {
        assertEquals(LoginViewState.SignedIn, signedInViewModel.uiState.value)
    }

    @Test
    fun testInitializeSigningIn() {
        assertEquals(LoginViewState.SigningIn, notSignedInViewModel.uiState.value)
    }

    @Test
    fun testSignInSuccess() {
        val activityResult: ActivityResult = mockk<ActivityResult>()
        coEvery { signInUseCase.invoke(any()) } returns Result.success(Unit)
        signedInViewModel.signIn(activityResult)
        assertEquals(LoginViewState.SignedIn, signedInViewModel.uiState.value)
    }

    @Test
    fun testSignInFailure() {
        val activityResult: ActivityResult = mockk<ActivityResult>()
        coEvery { signInUseCase.invoke(any()) } returns Result.failure(Exception())
        signedInViewModel.signIn(activityResult)
        assertEquals(LoginViewState.Error, signedInViewModel.uiState.value)
    }

}