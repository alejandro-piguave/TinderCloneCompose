package com.apiguave.tinderclonecompose.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.apiguave.tinderclonecompose.R
import com.apiguave.tinderclonecompose.ui.components.*
import com.apiguave.tinderclonecompose.ui.theme.Green1
import com.apiguave.tinderclonecompose.ui.theme.Green2
import com.apiguave.tinderclonecompose.ui.theme.Orange
import com.apiguave.tinderclonecompose.ui.theme.Pink
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomeView(onNavigateToEditProfile: () -> Unit,
             onNavigateToMatchList: () -> Unit,
             homeViewModel: HomeViewModel = viewModel()){

    val scope = rememberCoroutineScope()
    val uiState by homeViewModel.uiState.collectAsState()
    val swipeStates = uiState.profileList.map { rememberSwipeableCardState() }

    Surface {
        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
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
                            onSwiped = { direction ->
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
    }
}


