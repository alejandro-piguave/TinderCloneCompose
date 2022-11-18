package com.apiguave.tinderclonecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
                        LoginView(onNavigateToSignUp = {
                            navController.navigate("signup")
                        })
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
                        SignUpView(onAddPicture = {
                            navController.navigate("add_picture")
                        })
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
                        AddPictureView(onCloseClicked = {
                            navController.popBackStack()
                        })
                    }
                }

            }
        }
    }
}

@Composable
fun LoginView(onNavigateToSignUp: () -> Unit) {
    Column(
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
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Spacer(modifier = Modifier.weight(1.0f))
        Image(
            painter = painterResource(id = R.drawable.tinder_logo),
            contentDescription = stringResource(id = R.string.app_name),
            modifier = Modifier.padding(all = 24.dp)
        )
        Button(
            onClick = { /* ... */ },
            contentPadding = PaddingValues(
                start = 20.dp,
                top = 12.dp,
                end = 20.dp,
                bottom = 12.dp
            ),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
        ) {
            // Inner content including an icon and a text label
            Image(
                painter = painterResource(id = R.drawable.google_logo_48),
                contentDescription = stringResource(id = R.string.app_name)
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text("Sign In with Google", color = Color.Gray)
        }
        Spacer(modifier = Modifier.weight(1.0f))
        TextButton(onClick = onNavigateToSignUp) {
            Text("Create account", color = Color.White)
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