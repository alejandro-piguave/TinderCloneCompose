package com.apiguave.onboarding_ui.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import com.apiguave.onboarding_ui.R

@Composable
fun AnimatedLogo(modifier: Modifier = Modifier, isAnimating: Boolean){
    val infiniteTransition = rememberInfiniteTransition()
    val animatedLogoScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.25f,
        animationSpec = infiniteRepeatable(animation = tween(1000), repeatMode = RepeatMode.Reverse)
    )
    Image(
        painter = painterResource(id = R.drawable.tinder_logo),
        contentDescription = null,
        modifier = modifier.then(if(isAnimating) Modifier.graphicsLayer(scaleX = animatedLogoScale, scaleY = animatedLogoScale) else Modifier)
    )
}