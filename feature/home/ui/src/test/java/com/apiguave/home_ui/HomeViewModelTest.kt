package com.apiguave.home_ui

import com.apiguave.message_domain.usecases.SendMessageUseCase
import com.apiguave.picture_domain.usecases.GetPictureUseCase
import com.apiguave.profile_domain.usecases.GetProfilesUseCase
import com.apiguave.profile_domain.usecases.PassProfileUseCase
import com.apiguave.home_ui.usecases.LikeProfileUseCase
import org.junit.Before
import io.mockk.mockk

class HomeViewModelTest {

    private val getPictureUseCase: GetPictureUseCase = mockk()
    private val sendMessageUseCase: SendMessageUseCase = mockk()
    private val passProfileUseCase: PassProfileUseCase = mockk()
    private val likeProfileUseCase: LikeProfileUseCase = mockk()
    private val getProfilesUseCase: GetProfilesUseCase = mockk()
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        viewModel = HomeViewModel(getProfilesUseCase, likeProfileUseCase, passProfileUseCase, sendMessageUseCase, getPictureUseCase)
    }
}