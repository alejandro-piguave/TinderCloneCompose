package com.apiguave.tinderclonecompose.signup

import androidx.compose.ui.text.input.TextFieldValue
import com.apiguave.tinderclonecompose.MainDispatcherRule
import com.apiguave.tinderclonedomain.usecase.GetMaxBirthdateUseCase
import com.apiguave.tinderclonedomain.usecase.SignUpUseCase
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate

class SignUpViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val signUpUseCase: SignUpUseCase = mockk()
    private val maxBirthdate = LocalDate.of(2000, 1, 1)
    private val getMaxBirthdateUseCase: GetMaxBirthdateUseCase = mockk<GetMaxBirthdateUseCase> {
        every<LocalDate> { this@mockk.invoke() } returns LocalDate.of(2000, 1, 1)
    }
    private lateinit var viewModel: SignUpViewModel

    @Before
    fun initialize() {
        viewModel = SignUpViewModel(getMaxBirthdateUseCase, signUpUseCase)
    }

    @Test
    fun testInit() {
        val state = viewModel.uiState.value
        assertEquals(maxBirthdate, state.maxBirthDate)
        assertEquals(maxBirthdate, state.birthDate)
    }

    @Test
    fun testSetBirthdate() {
        val testDate = LocalDate.of(1998, 4, 2)
        viewModel.setBirthDate(testDate)
        val state = viewModel.uiState.value
        assertEquals(testDate, state.birthDate)
    }

    @Test
    fun testSetBio() {
        val testBio = "hola, esto es un test"
        viewModel.setBio(TextFieldValue(testBio))
        val state = viewModel.uiState.value
        assertEquals(testBio, state.bio.text)
    }

    @Test
    fun testSetName() {
        val testName = "John Doe"
        viewModel.setName(TextFieldValue(testName))
        val state = viewModel.uiState.value
        assertEquals(testName, state.name.text)
    }

    @Test
    fun testSetGenderIndex() {
        val testGenderIndex = 0
        viewModel.setGenderIndex(testGenderIndex)
        val state = viewModel.uiState.value
        assertEquals(testGenderIndex, state.genderIndex)
    }

    @Test
    fun testSetOrientationIndex() {
        val testOrientationIndex = 0
        viewModel.setOrientationIndex(testOrientationIndex)
        val state = viewModel.uiState.value
        assertEquals(testOrientationIndex, state.orientationIndex)
    }

    @Test
    fun testCloseDialog() {
        viewModel.closeDialog()
        val state = viewModel.uiState.value
        assertEquals(SignUpDialogState.NoDialog, state.dialogState)
    }

    @Test
    fun testShowConfirmDeletionDialog() {
        val testIndex = 2
        viewModel.showConfirmDeletionDialog(testIndex)
        val state = viewModel.uiState.value
        assertEquals(SignUpDialogState.DeleteConfirmationDialog(testIndex), state.dialogState)
    }

    @Test
    fun testShowSelectPictureDialog() {
        viewModel.showSelectPictureDialog()
        val state = viewModel.uiState.value
        assertEquals(SignUpDialogState.SelectPictureDialog, state.dialogState)
    }
}