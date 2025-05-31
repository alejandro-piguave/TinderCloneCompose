package com.apiguave.home_ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apiguave.core_ui.components.AnimatedGradientLogo
import com.apiguave.core_ui.components.GradientButton
import com.apiguave.core_ui.theme.Green1
import com.apiguave.core_ui.theme.Green2
import com.apiguave.core_ui.theme.Orange
import com.apiguave.core_ui.theme.Pink
import com.apiguave.core_ui.theme.TinderCloneComposeTheme
import com.apiguave.home_ui.components.ProfileCardView
import com.apiguave.home_ui.components.RoundGradientButton
import com.apiguave.home_ui.components.SwipingDirection
import com.apiguave.home_ui.components.TopBarIcon
import com.apiguave.home_ui.components.dialogs.NewMatchDialog
import com.apiguave.home_ui.components.rememberSwipeableCardState
import com.apiguave.home_ui.components.swipableCard
import kotlinx.coroutines.*

@Composable
fun HomeView(
    uiState: HomeViewState,
    navigateToEditProfile: () -> Unit,
    navigateToMatchList: () -> Unit,
    removeLastProfile: () -> Unit,
    fetchProfiles: () -> Unit,
    swipeUser: (ProfileState, Boolean) -> Unit,
    onSendMessage: (String, String) -> Unit,
    onCloseDialog: () -> Unit) {
    val scope = rememberCoroutineScope()

    when(uiState.dialogState) {
        is HomeViewDialogState.NewMatchDialog -> {
            NewMatchDialog(pictureStates = uiState.dialogState.pictureStates, onSendMessage = { onSendMessage(uiState.dialogState.match.id, it) }, onCloseClicked = onCloseDialog)
        }
        else -> {}
    }
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                TopBarIcon(
                    imageVector = Icons.Filled.AccountCircle,
                    onClick = navigateToEditProfile
                )
                Spacer(Modifier.weight(1f))
                TopBarIcon(resId = R.drawable.tinder_logo, modifier = Modifier.size(32.dp))
                Spacer(Modifier.weight(1f))
                TopBarIcon(
                    resId = R.drawable.ic_baseline_message_24,
                    onClick = navigateToMatchList
                )
            }
        }
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when(uiState.contentState){
                is HomeViewContentState.Error-> {
                    Spacer(Modifier.weight(1f))
                    Text(modifier = Modifier.padding(horizontal = 8.dp),
                        text = uiState.contentState.message, color = Color.Gray, fontSize = 16.sp, textAlign = TextAlign.Center)
                    Spacer(Modifier.height(12.dp))
                    GradientButton(onClick = {
                        scope.launch {
                            delay(200)
                            fetchProfiles()
                        }
                    }) {
                        Text(stringResource(id = R.string.retry))
                    }
                    Spacer(Modifier.weight(1f))
                }
                HomeViewContentState.Loading -> {
                    Spacer(Modifier.weight(1f))
                    AnimatedGradientLogo(Modifier.fillMaxWidth(.4f))
                    Spacer(Modifier.weight(1f))
                }
                is HomeViewContentState.Success -> {
                    Spacer(Modifier.weight(1f))
                    Box(Modifier.padding(horizontal = 12.dp)) {
                        Text(
                            text = stringResource(id = R.string.no_more_profiles),
                            color = Color.Gray,
                            fontSize = 20.sp
                        )
                        val localDensity = LocalDensity.current
                        var buttonRowHeightDp by remember { mutableStateOf(0.dp) }

                        val swipeStates = uiState.contentState.profileStates.map { rememberSwipeableCardState() }
                        uiState.contentState.profileStates.forEachIndexed { index, profileState ->
                            ProfileCardView(profileState.profile, profileState.pictureStates,
                                modifier = Modifier.swipableCard(
                                    state = swipeStates[index],
                                    onSwiped = {
                                        swipeUser(
                                            profileState,
                                            it == SwipingDirection.Right
                                        )
                                        removeLastProfile()
                                    }
                                ),
                                contentModifier = Modifier.padding(bottom = buttonRowHeightDp.plus(8.dp))
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomCenter)
                                .padding(vertical = 10.dp)
                                .onGloballyPositioned { coordinates ->
                                    buttonRowHeightDp =
                                        with(localDensity) { coordinates.size.height.toDp() }
                                },
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Spacer(Modifier.weight(1f))
                            RoundGradientButton(
                                onClick = {
                                    scope.launch {
                                        swipeStates.last().swipe(SwipingDirection.Left)
                                        removeLastProfile()
                                    }
                                },
                                enabled = swipeStates.isNotEmpty(),
                                imageVector = Icons.Filled.Close, color1 = Pink, color2 = Orange
                            )
                            Spacer(Modifier.weight(.5f))
                            RoundGradientButton(
                                onClick = {
                                    scope.launch {
                                        swipeStates.last().swipe(SwipingDirection.Right)
                                        removeLastProfile()
                                    }
                                },
                                enabled = swipeStates.isNotEmpty(),
                                imageVector = Icons.Filled.Favorite,
                                color1 = Green1,
                                color2 = Green2
                            )
                            Spacer(Modifier.weight(1f))
                        }
                    }
                    Spacer(Modifier.weight(1f))
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeViewPreview() {
    TinderCloneComposeTheme {
        HomeView(
            uiState = HomeViewState(HomeViewDialogState.NoDialog, HomeViewContentState.Loading),
            navigateToEditProfile = {},
            navigateToMatchList = {},
            removeLastProfile = {},
            fetchProfiles = {},
            swipeUser = { _, _ ->

            },
            onCloseDialog = {},
            onSendMessage = { _, _ ->}
        )
    }
}
