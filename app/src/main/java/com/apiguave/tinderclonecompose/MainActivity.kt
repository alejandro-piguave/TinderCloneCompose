package com.apiguave.tinderclonecompose

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import com.apiguave.tinderclonecompose.ui.*
import com.apiguave.tinderclonecompose.ui.theme.Orange
import com.apiguave.tinderclonecompose.ui.theme.Pink
import com.apiguave.tinderclonecompose.ui.theme.TinderCloneComposeTheme
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

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

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TinderCloneComposeTheme {
                val navController = rememberAnimatedNavController()
                val imageUris = remember { mutableStateListOf<Uri>() }

                AnimatedNavHost(navController = navController, startDestination = Routes.Login) {
                    animatedComposable(Routes.Login) {
                        LoginView(
                            onNavigateToSignUp = {
                                navController.navigate(Routes.SignUp)
                            },
                            onNavigateTohHome = {
                                navController.navigate(Routes.Home)
                            }
                        )
                    }
                    animatedComposable(Routes.SignUp) {
                        SignUpView(
                            imageUris = imageUris,
                            onAddPicture = {
                                navController.navigate(Routes.SignUp)
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
                                navController.navigate(Routes.SignUp)
                            }
                        )
                    }
                    animatedComposable(Routes.MatchList){
                        MatchListView()
                    }
                    animatedComposable(Routes.Chat){
                        ChatView()
                    }
                }

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
        slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(TRANSITION_DURATION))
                          },
        exitTransition = {
            slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(TRANSITION_DURATION))
        },
        popEnterTransition = {
            slideIntoContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(TRANSITION_DURATION))
        },
        popExitTransition = {
            slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(TRANSITION_DURATION))
        }, content = content )
}