package com.apiguave.onboarding_ui.signup

import android.net.Uri
import androidx.activity.result.ActivityResult
import androidx.compose.ui.text.input.TextFieldValue
import com.apiguave.onboarding_ui.extensions.toProviderAccount
import com.apiguave.core_ui.model.PictureState
import com.apiguave.auth_domain.model.Account
import com.apiguave.onboarding_ui.MainDispatcherRule
import com.apiguave.onboarding_domain.CreateProfileUseCase
import com.apiguave.onboarding_ui.create_profile.CreateProfileDialogState
import com.apiguave.onboarding_ui.create_profile.CreateProfileViewModel
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate

class CreateProfileViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val createProfileUseCase: CreateProfileUseCase = mockk()
    private val maxBirthdate = LocalDate.of(2000, 1, 1)
    private val mockAccount = Account("john.doe@gmail.com", "123456890")
    private lateinit var viewModel: CreateProfileViewModel

    @Before
    fun initialize() {
        viewModel = CreateProfileViewModel(createProfileUseCase)
        mockkStatic(ActivityResult::toProviderAccount)
        coEvery { any<ActivityResult>().toProviderAccount() } returns mockAccount
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
        assertEquals(CreateProfileDialogState.NoDialog, state.dialogState)
    }

    @Test
    fun testShowConfirmDeletionDialog() {
        val testIndex = 2
        viewModel.showConfirmDeletionDialog(testIndex)
        val state = viewModel.uiState.value
        assertEquals(CreateProfileDialogState.DeleteConfirmationDialog(testIndex), state.dialogState)
    }

    @Test
    fun testShowSelectPictureDialog() {
        viewModel.showSelectPictureDialog()
        val state = viewModel.uiState.value
        assertEquals(CreateProfileDialogState.SelectPictureDialog, state.dialogState)
    }

    @Test
    fun testAddPicture() {
        val testPicture = mockk<Uri>()
        viewModel.addPicture(testPicture)
        val state = viewModel.uiState.value
        assertEquals(listOf(PictureState.Local(testPicture)), state.pictures)
    }

    @Test
    fun testRemovePicture() {
        val testPicture = mockk<Uri>()
        viewModel.addPicture(testPicture)
        val state = viewModel.uiState.value
        assertEquals(listOf(PictureState.Local(testPicture)), state.pictures)
        viewModel.removePictureAt(0)
        val newState = viewModel.uiState.value
        assertEquals(emptyList<Uri>(), newState.pictures)
    }

    @Test
    fun testCreateProfileSuccess() {
        coEvery { createProfileUseCase.invoke(any(),any(),any(),any(),any(),any()) } returns Result.success(Unit)

        viewModel.setGenderIndex(0)
        viewModel.setOrientationIndex(0)
        viewModel.createProfile()
        val state = viewModel.uiState.value
        assertEquals(true, state.isUserSignedIn)
    }

    @Test
    fun testCreateProfileFailure() {
        coEvery { createProfileUseCase.invoke(any(),any(),any(),any(),any(),any()) } returns Result.failure(Exception())

        viewModel.setGenderIndex(0)
        viewModel.setOrientationIndex(0)
        viewModel.createProfile()
        val state = viewModel.uiState.value
        assertEquals(CreateProfileDialogState.ErrorDialog, state.dialogState)
    }
    
}