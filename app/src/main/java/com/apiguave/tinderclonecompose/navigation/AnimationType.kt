package com.apiguave.tinderclonecompose.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.*

import androidx.navigation.NavBackStackEntry

const val SLIDE_TRANSITION_DURATION = 400
const val FADE_TRANSITION_DURATION = 1000

@OptIn(ExperimentalAnimationApi::class)
typealias EnterTransitionScope = (AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition?)
@OptIn(ExperimentalAnimationApi::class)
typealias ExitTransitionScope = (AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition?)

@OptIn(ExperimentalAnimationApi::class)
enum class AnimationType(
    val enterTransition: EnterTransitionScope,
    val exitTransition: ExitTransitionScope,
    val popEnterTransition: EnterTransitionScope,
    val popExitTransition: ExitTransitionScope
){
    SLIDE(
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentScope.SlideDirection.Left,
                animationSpec = tween(SLIDE_TRANSITION_DURATION)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentScope.SlideDirection.Left,
                animationSpec = tween(SLIDE_TRANSITION_DURATION)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                towards = AnimatedContentScope.SlideDirection.Right,
                animationSpec = tween(SLIDE_TRANSITION_DURATION)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentScope.SlideDirection.Right,
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
                towards = AnimatedContentScope.SlideDirection.Left,
                animationSpec = tween(SLIDE_TRANSITION_DURATION)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentScope.SlideDirection.Left,
                animationSpec = tween(SLIDE_TRANSITION_DURATION)
            )

        },
        popEnterTransition = {
            slideIntoContainer(
                towards = AnimatedContentScope.SlideDirection.Right,
                animationSpec = tween(SLIDE_TRANSITION_DURATION)
            )

        },
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentScope.SlideDirection.Right,
                animationSpec = tween(SLIDE_TRANSITION_DURATION)
            )
        }
    )
}