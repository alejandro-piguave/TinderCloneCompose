package com.apiguave.feature_chat.di

import com.apiguave.feature_chat.chat.ChatViewModel
import com.apiguave.feature_chat.match_list.MatchListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val chatFeatureModule = module {
    //View models
    viewModel { ChatViewModel(get(), get(), get()) }
    viewModel { MatchListViewModel(get(), get()) }
}