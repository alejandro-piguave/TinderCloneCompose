package com.apiguave.tinderclonecompose.ui.main

import android.net.Uri
import androidx.compose.animation.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import com.apiguave.tinderclonecompose.ui.*
import com.apiguave.tinderclonecompose.ui.chat.ChatView
import com.apiguave.tinderclonecompose.ui.chat.ChatViewModel
import com.apiguave.tinderclonecompose.ui.editprofile.EditProfileView
import com.apiguave.tinderclonecompose.ui.home.HomeView
import com.apiguave.tinderclonecompose.ui.login.LoginView
import com.apiguave.tinderclonecompose.ui.matchlist.MatchListView
import com.apiguave.tinderclonecompose.ui.newmatch.NewMatchView
import com.apiguave.tinderclonecompose.ui.newmatch.NewMatchViewModel
import com.apiguave.tinderclonecompose.ui.signup.SignUpView
import com.apiguave.tinderclonecompose.ui.theme.TinderCloneComposeTheme
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.android.gms.auth.api.signin.GoogleSignInClient

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainContent(signInClient: GoogleSignInClient){
    TinderCloneComposeTheme {
        val navController = rememberAnimatedNavController()
        val imageUris = remember { mutableStateListOf<Uri>() }

        val chatViewModel: ChatViewModel = viewModel()
        val newMatchViewModel: NewMatchViewModel = viewModel()
        AnimatedNavHost(navController = navController, startDestination = Routes.Login) {

            animatedComposable(Routes.Login) {
                LoginView(
                    signInClient = signInClient,
                    onNavigateToSignUp = {
                        navController.navigate(Routes.SignUp)
                    },
                    onNavigateToHome = {
                        navController.navigate(Routes.Home){
                            popUpTo(Routes.Login){
                                inclusive = true
                            }
                        }
                    }
                )
            }

            animatedComposable(Routes.SignUp) {
                SignUpView(
                    signInClient = signInClient,
                    imageUris = imageUris,
                    onAddPicture = {
                        navController.navigate(Routes.AddPicture)
                    },
                    onNavigateToHome = {
                        navController.navigate(Routes.Home){
                            popUpTo(Routes.SignUp){
                                inclusive = true
                            }
                        }
                    }
                )
            }

            animatedComposable(Routes.AddPicture){
                AddPictureView(
                    onCloseClicked = {
                        navController.popBackStack()
                    },
                    onReceiveUri = {
                        imageUris.add(it)
                        navController.popBackStack()
                    }
                )
            }

            animatedComposable(Routes.Home, animationType = AnimationType.HOME) {
                HomeView(
                    newMatchViewModel = newMatchViewModel,
                    onNavigateToEditProfile = {
                        navController.navigate(Routes.EditProfile)
                    },
                    onNavigateToMatchList = {
                        navController.navigate(Routes.MatchList)
                    },
                    onNavigateToNewMatch = {
                        navController.navigate(Routes.NewMatch)
                    }
                )
            }

            animatedComposable(Routes.NewMatch, animationType = AnimationType.FADE){
                NewMatchView(newMatchViewModel,
                    onCloseClicked = {
                        navController.popBackStack()
                    }
                )
            }

            animatedComposable(Routes.EditProfile){
                EditProfileView(
                    signInClient = signInClient,
                    imageUris = imageUris,
                    onAddPicture = {
                        navController.navigate(Routes.AddPicture)
                    },
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
                MatchListView(
                    onNavigateToChatView = {
                        navController.navigate(Routes.Chat)
                    },
                    onArrowBackPressed = {
                        navController.popBackStack()
                    },
                    chatViewModel = chatViewModel
                )
            }

            animatedComposable(Routes.Chat){
                ChatView(
                    onArrowBackPressed = { navController.popBackStack() },
                    viewModel = chatViewModel
                )
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