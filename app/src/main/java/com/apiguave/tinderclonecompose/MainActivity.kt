package com.apiguave.tinderclonecompose

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import com.apiguave.tinderclonecompose.ui.AddPictureView
import com.apiguave.tinderclonecompose.ui.LoginView
import com.apiguave.tinderclonecompose.ui.SignUpView
import com.apiguave.tinderclonecompose.ui.theme.Orange
import com.apiguave.tinderclonecompose.ui.theme.Pink
import com.apiguave.tinderclonecompose.ui.theme.TinderCloneComposeTheme
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

const val TRANSITION_DURATION = 400

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TinderCloneComposeTheme {
                val navController = rememberAnimatedNavController()
                val imageUris = remember { mutableStateListOf<Uri>() }

                AnimatedNavHost(navController = navController, startDestination = "login") {
                    composable("login",
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
                        }
                    ) {
                        LoginView(
                            onNavigateToSignUp = {
                                navController.navigate("signup")
                            }
                        )
                    }
                    composable("signup",
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
                        }) {
                        SignUpView(
                            imageUris = imageUris,
                            onAddPicture = {
                                navController.navigate("add_picture")
                            }
                        )
                    }
                    composable("add_picture",
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
                        }){
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
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TinderCloneComposeTheme {
        Surface {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Orange,
                                Pink
                            )
                        )
                    ),
            ) {
            }
        }
    }
}