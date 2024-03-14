package com.apiguave.tinderclonecompose.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.apiguave.tinderclonecompose.ui.extension.getRandomProfile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    navigateToEditProfile: () -> Unit,
    navigateToMatchList: () -> Unit) {
    val homeViewModel: HomeViewModel = koinViewModel()
    val uiState by homeViewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
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
            scope.launch(Dispatchers.Main) {
                val profiles = withContext(Dispatchers.IO) {
                    (0 until profileCount).map {
                        async {
                            getRandomProfile(context)
                        }
                    }.awaitAll()
                }
                homeViewModel.createProfiles(profiles)
            }
        },

    )
}