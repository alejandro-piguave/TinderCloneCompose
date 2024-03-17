package com.apiguave.tinderclonecompose.ui.main

import androidx.compose.animation.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import org.koin.androidx.compose.getViewModel
import androidx.navigation.*
import com.apiguave.tinderclonecompose.R
import com.apiguave.tinderclonecompose.ui.chat.ChatView
import com.apiguave.tinderclonecompose.ui.chat.ChatViewModel
import com.apiguave.tinderclonecompose.ui.editprofile.EditProfileScreen
import com.apiguave.tinderclonecompose.ui.home.HomeScreen
import com.apiguave.tinderclonecompose.ui.login.LoginScreen
import com.apiguave.tinderclonecompose.ui.matchlist.MatchListView
import com.apiguave.tinderclonecompose.ui.matchlist.MatchListViewModel
import com.apiguave.tinderclonecompose.ui.signup.SignUpView
import com.apiguave.tinderclonecompose.ui.signup.SignUpViewModel
import com.apiguave.tinderclonecompose.ui.theme.TinderCloneComposeTheme
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainContent(signInClient: GoogleSignInClient){
    TinderCloneComposeTheme {
        val navController = rememberAnimatedNavController()
        val chatViewModel: ChatViewModel = getViewModel()
        AnimatedNavHost(navController = navController, startDestination = Routes.Login) {
            animatedComposable(Routes.Login) {
                LoginScreen(
                    signInClient = signInClient,
                    onNavigateToHome = {
                        navController.navigate(Routes.SignUp)
                    },
                    onNavigateToSignUp = {
                        navController.navigate(Routes.Home){
                            popUpTo(Routes.Login){
                                inclusive = true
                            }
                        }
                    }
                )
            }

            animatedComposable(Routes.SignUp) {
                val signUpViewModel: SignUpViewModel = koinViewModel()
                val uiState by signUpViewModel.uiState.collectAsState()
                SignUpView(
                    uiState = uiState,
                    signInClient = signInClient,
                    onPictureSelected = {
                        signUpViewModel.addPicture(it)
                    },
                    onNavigateToHome = {
                        navController.navigate(Routes.Home){
                            popUpTo(Routes.SignUp){
                                inclusive = true
                            }
                        }
                    },
                    removePictureAt = signUpViewModel::removePictureAt,
                    signUp = signUpViewModel::signUp
                )
            }

            animatedComposable(Routes.Home, animationType = AnimationType.HOME) {
                HomeScreen(
                    navigateToEditProfile = {
                        navController.navigate(Routes.EditProfile)
                    },
                    navigateToMatchList = {
                        navController.navigate(Routes.MatchList)
                    }
                )
            }

            animatedComposable(Routes.EditProfile){
                EditProfileScreen(
                    signInClient = signInClient,
                    onProfileEdited = navController::popBackStack,
                    onSignedOut = {
                        navController.navigate(Routes.Login){
                            popUpTo(Routes.Home){
                                inclusive = true
                            }
                        }
                    }
                )
            }

            animatedComposable(Routes.MatchList){
                val viewModel: MatchListViewModel = koinViewModel()
                val uiState by viewModel.uiState.collectAsState()
                MatchListView(
                    uiState = uiState,
                    navigateToMatch = {
                        chatViewModel.setMatch(it)
                        navController.navigate(Routes.Chat)
                    },
                    onArrowBackPressed =  navController::popBackStack,
                    fetchMatches = viewModel::fetchMatches
                )
            }

            animatedComposable(Routes.Chat){
                val chatMatch by chatViewModel.match.collectAsState()
                chatMatch?.let {
                    val messages by chatViewModel.getMessages(it.id).collectAsState(
                        initial = listOf()
                    )
                    ChatView(
                        match = it,
                        messages = messages,
                        onArrowBackPressed = navController::popBackStack,
                        sendMessage = chatViewModel::sendMessage,
                    )
                }  ?: run{
                    Text(modifier = Modifier.fillMaxSize(), textAlign = TextAlign.Center, text = stringResource(id = R.string.no_match_value_passed))
                }
            }
        }
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.animatedComposable(
    route: String,
    animationType: AnimationType = AnimationType.SLIDE,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit
) {
    composable(route, arguments, deepLinks,
        enterTransition = animationType.enterTransition,
        exitTransition = animationType.exitTransition,
        popEnterTransition = animationType.popEnterTransition,
        popExitTransition = animationType.popExitTransition,
        content = content
    )
}