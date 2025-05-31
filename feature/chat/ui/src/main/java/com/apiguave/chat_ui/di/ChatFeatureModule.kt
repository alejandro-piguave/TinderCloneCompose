package com.apiguave.chat_ui.di

import com.apiguave.chat_ui.chat.ChatViewModel
import com.apiguave.chat_ui.match_list.MatchListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val chatFeatureModule = module {
    //View models
    viewModel { ChatViewModel(get(), get(), get()) }
    viewModel { MatchListViewModel(get(), get()) }
}