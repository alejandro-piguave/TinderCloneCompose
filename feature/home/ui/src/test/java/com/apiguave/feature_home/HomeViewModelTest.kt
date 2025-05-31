package com.apiguave.feature_home

import com.apiguave.domain_message.usecases.SendMessageUseCase
import com.apiguave.domain_picture.usecases.GetPictureUseCase
import com.apiguave.domain_profile.usecases.GetProfilesUseCase
import com.apiguave.domain_profile.usecases.PassProfileUseCase
import com.apiguave.feature_home.usecases.LikeProfileUseCase
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