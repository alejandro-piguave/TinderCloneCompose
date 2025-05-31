package com.apiguave.home_ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    navigateToEditProfile: () -> Unit,
    navigateToMatchList: () -> Unit) {
    val homeViewModel: HomeViewModel = koinViewModel()
    val uiState by homeViewModel.uiState.collectAsState()
    HomeView(
        uiState = uiState,
        navigateToEditProfile = navigateToEditProfile,
        navigateToMatchList = navigateToMatchList,
        removeLastProfile = homeViewModel::removeLastProfile,
        fetchProfiles = homeViewModel::fetchProfiles,
        swipeUser = homeViewModel::swipeUser,
        onCloseDialog = homeViewModel::closeDialog,
        onSendMessage = homeViewModel::sendMessage,
    )
}