package com.apiguave.tinderclonecompose.navigation

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.apiguave.feature_auth.create_profile.CreateProfileScreen
import com.apiguave.core_ui.theme.TinderCloneComposeTheme
import com.apiguave.feature_auth.login.LoginScreen
import com.apiguave.feature_auth.splash.SplashScreen
import com.apiguave.feature_home.HomeScreen
import com.apiguave.feature_chat.chat.ChatScreen
import com.apiguave.feature_chat.chat.ChatViewModel
import com.apiguave.feature_chat.match_list.MatchListScreen
import com.apiguave.feature_profile.EditProfileScreen
import org.koin.androidx.compose.getViewModel

@Composable
fun NavigationGraph() {
    TinderCloneComposeTheme {
        val navController = rememberNavController()
        val chatViewModel: ChatViewModel = getViewModel()
        NavHost(navController = navController, startDestination = Routes.Splash) {
            animatedComposable(Routes.Splash) {
                SplashScreen(
                   onNavigateToHome = {
                       navController.navigate(Routes.Home){
                           popUpTo(Routes.Splash){
                               inclusive = true
                           }
                       }
                   },
                    onNavigateToSignIn = {
                        navController.navigate(Routes.Login){
                            popUpTo(Routes.Splash){
                                inclusive = true
                            }
                        }
                    },
                    onNavigateToCreateProfile = {
                        navController.navigate(Routes.SignUp){
                            popUpTo(Routes.Splash){
                                inclusive = true
                            }
                        }
                    }
                )
            }

            animatedComposable(Routes.Login, animationType = AnimationType.HOME) {
                LoginScreen(
                    onNavigateToHome = {
                        navController.navigate(Routes.Home){
                            popUpTo(Routes.Login){
                                inclusive = true
                            }
                        }
                    },
                    onNavigateToSignUp = {
                        navController.navigate(Routes.SignUp)
                    }
                )
            }

            animatedComposable(Routes.SignUp) {
                CreateProfileScreen(
                    onNavigateToHome = {
                        navController.navigate(Routes.Home){
                            popUpTo(Routes.SignUp){
                                inclusive = true
                            }
                        }
                    }
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
                MatchListScreen(
                    onArrowBackPressed = navController::popBackStack,
                    navigateToMatch = {
                        chatViewModel.setMatchState(it)
                        navController.navigate(Routes.Chat)
                    }
                )
            }

            animatedComposable(Routes.Chat){
                ChatScreen(
                    onArrowBackPressed = navController::popBackStack,
                    viewModel = chatViewModel
                )
            }
        }
    }
}

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