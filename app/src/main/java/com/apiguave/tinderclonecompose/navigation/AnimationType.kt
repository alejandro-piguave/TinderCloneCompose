package com.apiguave.tinderclonecompose.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavBackStackEntry

const val SLIDE_TRANSITION_DURATION = 400
const val FADE_TRANSITION_DURATION = 1000

typealias EnterTransitionScope = (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)
typealias ExitTransitionScope = (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)

enum class AnimationType(
    val enterTransition: EnterTransitionScope,
    val exitTransition: ExitTransitionScope,
    val popEnterTransition: EnterTransitionScope,
    val popExitTransition: ExitTransitionScope
){
    SLIDE(
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(SLIDE_TRANSITION_DURATION)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(SLIDE_TRANSITION_DURATION)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(SLIDE_TRANSITION_DURATION)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(SLIDE_TRANSITION_DURATION)
            )
        },
    ),
    FADE(
        enterTransition = { fadeIn(tween(FADE_TRANSITION_DURATION)) },
        exitTransition = { fadeOut(tween(FADE_TRANSITION_DURATION)) },
        popEnterTransition = { fadeIn(tween(FADE_TRANSITION_DURATION)) },
        popExitTransition = { fadeOut(tween(FADE_TRANSITION_DURATION)) },
    ),
    HOME(
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(SLIDE_TRANSITION_DURATION)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(SLIDE_TRANSITION_DURATION)
            )

        },
        popEnterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(SLIDE_TRANSITION_DURATION)
            )

        },
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(SLIDE_TRANSITION_DURATION)
            )
        }
    )
}