package com.apiguave.tinderclonecompose.ui.main

import androidx.compose.animation.*
import androidx.compose.runtime.Composable
import org.koin.androidx.compose.getViewModel
import androidx.navigation.*
import com.apiguave.tinderclonecompose.ui.*
import com.apiguave.tinderclonecompose.ui.chat.ChatView
import com.apiguave.tinderclonecompose.ui.chat.ChatViewModel
import com.apiguave.tinderclonecompose.ui.components.AddPictureView
import com.apiguave.tinderclonecompose.ui.editprofile.EditProfileView
import com.apiguave.tinderclonecompose.ui.editprofile.EditProfileViewModel
import com.apiguave.tinderclonecompose.ui.home.HomeView
import com.apiguave.tinderclonecompose.ui.login.LoginView
import com.apiguave.tinderclonecompose.ui.matchlist.MatchListView
import com.apiguave.tinderclonecompose.ui.newmatch.NewMatchView
import com.apiguave.tinderclonecompose.ui.newmatch.NewMatchViewModel
import com.apiguave.tinderclonecompose.ui.signup.SignUpView
import com.apiguave.tinderclonecompose.ui.signup.SignUpViewModel
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

        val chatViewModel: ChatViewModel = getViewModel()
        val newMatchViewModel: NewMatchViewModel = getViewModel()
        val editProfileViewModel: EditProfileViewModel = getViewModel()
        val signUpViewModel: SignUpViewModel = getViewModel()
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
                    viewModel = signUpViewModel,
                    signInClient = signInClient,
                    onAddPicture = {
                        navController.navigate(Routes.getAddPictureRoute(Routes.SignUp))
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

            animatedComposable(Routes.AddPicture, arguments = listOf(navArgument(Arguments.Caller){ type = NavType.StringType})){
                AddPictureView(
                    onCloseClicked = {
                        navController.popBackStack()
                    },
                    onReceiveUri = { uri, caller ->
                        if(caller == Routes.SignUp){
                            signUpViewModel.addPicture(uri)
                        }else if(caller == Routes.EditProfile){
                            editProfileViewModel.addPicture(uri)
                        }
                        navController.popBackStack()
                    },
                    caller = it.arguments?.getString(Arguments.Caller)
                )
            }

            animatedComposable(Routes.Home, animationType = AnimationType.HOME) {
                HomeView(
                    newMatchViewModel = newMatchViewModel,
                    editProfileViewModel = editProfileViewModel,
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
                    viewModel = editProfileViewModel,
                    signInClient = signInClient,
                    onAddPicture = {
                        navController.navigate(Routes.getAddPictureRoute(Routes.EditProfile))
                    },
                    onProfileEdited = {
                        navController.popBackStack()
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