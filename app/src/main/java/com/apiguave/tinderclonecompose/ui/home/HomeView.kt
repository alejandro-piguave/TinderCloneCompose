package com.apiguave.tinderclonecompose.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.apiguave.tinderclonecompose.R
import com.apiguave.tinderclonecompose.data.allowProfileGeneration
import com.apiguave.tinderclonecompose.data.getRandomProfile
import com.apiguave.tinderclonecompose.ui.components.*
import com.apiguave.tinderclonecompose.ui.theme.Green1
import com.apiguave.tinderclonecompose.ui.theme.Green2
import com.apiguave.tinderclonecompose.ui.theme.Orange
import com.apiguave.tinderclonecompose.ui.theme.Pink
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomeView(onNavigateToEditProfile: () -> Unit,
             onNavigateToMatchList: () -> Unit,
             homeViewModel: HomeViewModel = viewModel()){
    var showGenerateProfilesDialog by remember{ mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val uiState by homeViewModel.uiState.collectAsState()
    val swipeStates = uiState.profileList.map { rememberSwipeableCardState() }
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                TopBarIcon(imageVector = Icons.Filled.AccountCircle, onClick = onNavigateToEditProfile)
                Spacer(Modifier.weight(1f))
                TopBarIcon(resId = R.drawable.tinder_logo, modifier = Modifier.size(32.dp))
                Spacer(Modifier.weight(1f))
                TopBarIcon(resId = R.drawable.ic_baseline_message_24, onClick = onNavigateToMatchList)
            }
        },
        floatingActionButton = {
            if(allowProfileGeneration){
                FloatingActionButton(
                    modifier = Modifier.size(40.dp),
                    onClick = { showGenerateProfilesDialog = true }) {
                    Icon(tint = Color.White,imageVector = Icons.Default.Add, contentDescription = null)
                }
            } else Unit
        }
    ){ padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding), horizontalAlignment = Alignment.CenterHorizontally) {
            if(uiState.isLoading){
                Spacer(Modifier.weight(1f))
                AnimatedGradientLogo(Modifier.fillMaxWidth())
                Spacer(Modifier.weight(1f))
            } else if(uiState.errorMessage != null){
                Spacer(Modifier.weight(1f))
                Text(text = uiState.errorMessage!!, color = Color.Gray, fontSize = 16.sp )
                Spacer(Modifier.height(12.dp))
                GradientButton(onClick = {
                    scope.launch {
                        delay(  200)
                        homeViewModel.fetchProfiles()
                    }
                }) {
                    Text(stringResource(id = R.string.retry))
                }
                Spacer(Modifier.weight(1f))
            } else {
                Spacer(Modifier.weight(1f))
                Box(Modifier.padding(horizontal = 20.dp)){
                    Text(text = stringResource(id = R.string.no_more_profiles), color = Color.Gray, fontSize = 20.sp )
                    uiState.profileList.forEachIndexed { index, profile  ->
                        ProfileCardView(profile, Modifier.swipableCard(
                            state = swipeStates[index],
                            onSwiped = {
                                homeViewModel.removeLastProfile()
                            }
                        )
                        )
                    }
                }

                Spacer(Modifier.weight(1f))

                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    Spacer(Modifier.weight(1f))
                    RoundGradientButton(
                        onClick = {
                            scope.launch {
                                swipeStates.last().swipe(SwipingDirection.Left)
                                homeViewModel.removeLastProfile()
                            }
                        },
                        enabled = swipeStates.isNotEmpty(),
                        imageVector = Icons.Filled.Close, color1 = Pink, color2 = Orange)
                    Spacer(Modifier.weight(.5f))
                    RoundGradientButton(onClick = {
                        scope.launch {
                            swipeStates.last().swipe(SwipingDirection.Right)
                            homeViewModel.removeLastProfile()
                        }
                    },
                        enabled = swipeStates.isNotEmpty(),
                        resId = R.drawable.ic_baseline_favorite_border_44, color1 =  Green1, color2 = Green2)
                    Spacer(Modifier.weight(1f))
                }
                Spacer(Modifier.height(24.dp))
            }
        }

        if(showGenerateProfilesDialog){
            val context = LocalContext.current
            GenerateProfilesDialog(
                onDismissRequest = { showGenerateProfilesDialog = false },
                onGenerate = { profileCount ->
                    showGenerateProfilesDialog = false
                    scope.launch {
                        val profiles = (0 until profileCount).map { async{ getRandomProfile(context) } }.awaitAll()
                        homeViewModel.createProfiles(profiles)
                    }
                }
            )
        }
    }
}


