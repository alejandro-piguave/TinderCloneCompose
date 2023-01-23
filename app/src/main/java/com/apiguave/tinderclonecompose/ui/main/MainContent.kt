package com.apiguave.tinderclonecompose.ui.main

import android.net.Uri
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import com.apiguave.tinderclonecompose.ui.*
import com.apiguave.tinderclonecompose.ui.editprofile.EditProfileView
import com.apiguave.tinderclonecompose.ui.login.LoginView
import com.apiguave.tinderclonecompose.ui.signup.SignUpView
import com.apiguave.tinderclonecompose.ui.theme.TinderCloneComposeTheme
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.android.gms.auth.api.signin.GoogleSignInClient

const val TRANSITION_DURATION = 400

class Routes{
    companion object{
        const val Login = "login"
        const val SignUp = "signup"
        const val AddPicture = "add_picture"
        const val Home = "home"
        const val EditProfile = "edit_profile"
        const val MatchList = "match_list"
        const val Chat = "chat"
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainContent(signInClient: GoogleSignInClient){
    TinderCloneComposeTheme {
        val navController = rememberAnimatedNavController()
        val imageUris = remember { mutableStateListOf<Uri>() }

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

            animatedComposable(Routes.Home) {
                HomeView(
                    onNavigateToEditProfile = {
                        navController.navigate(Routes.EditProfile)
                    },
                    onNavigateToMatchList = {
                        navController.navigate(Routes.MatchList)
                    }
                )
            }
            animatedComposable(Routes.EditProfile){
                EditProfileView(
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
                    }
                )
            }
            animatedComposable(Routes.Chat){
                ChatView(onArrowBackPressed = {
                    navController.popBackStack()
                })
            }
        }
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.animatedComposable(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit
) {
    composable(route, arguments, deepLinks,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentScope.SlideDirection.Left, animationSpec = tween(
                TRANSITION_DURATION
            )
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentScope.SlideDirection.Left, animationSpec = tween(
                TRANSITION_DURATION
            )
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentScope.SlideDirection.Right, animationSpec = tween(
                TRANSITION_DURATION
            )
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentScope.SlideDirection.Right, animationSpec = tween(
                TRANSITION_DURATION
            )
            )
        }, content = content )
}