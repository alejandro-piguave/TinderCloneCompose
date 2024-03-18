package com.apiguave.tinderclonecompose.ui.home

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
        onShowProfileGenerationDialog = { homeViewModel.showProfileGenerationDialog() },
        onCloseDialog = { homeViewModel.closeDialog() },
        onSendMessage = homeViewModel::sendMessage,
        onGenerateProfiles = { profileCount ->
            homeViewModel.closeDialog()
            homeViewModel.setLoading()
            homeViewModel.createProfiles(profileCount)
        },

    )
}