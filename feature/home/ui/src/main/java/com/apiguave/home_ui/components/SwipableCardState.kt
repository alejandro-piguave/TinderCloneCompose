package com.apiguave.home_ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp


enum class SwipingDirection {
    Left, Right, Up, Down
}

@Composable
fun rememberSwipeableCardState(): SwipeableCardState {
    val screenWidth = with(LocalDensity.current) {
        LocalConfiguration.current.screenWidthDp.dp.toPx()
    }
    val screenHeight = with(LocalDensity.current) {
        LocalConfiguration.current.screenHeightDp.dp.toPx()
    }
    return remember {
        SwipeableCardState(screenWidth, screenHeight)
    }
}


class SwipeableCardState(
    internal val maxWidth: Float,
    internal val maxHeight: Float,
) {
    val offset = Animatable(offset(0f, 0f), Offset.VectorConverter)

    /**
     * The [SwipingDirection] the card was swiped at.
     *
     * Null value means the card has not been swiped fully yet.
     */
    var swipedDirection: SwipingDirection? by mutableStateOf(null)
        private set

    internal suspend fun reset() {
        offset.animateTo(offset(0f, 0f), tween(400))
    }

    suspend fun swipe(direction: SwipingDirection, animationSpec: AnimationSpec<Offset> = tween(400)) {
        val endX = maxWidth * 1.5f
        val endY = maxHeight
        when (direction) {
            SwipingDirection.Left -> offset.animateTo(offset(x = -endX), animationSpec)
            SwipingDirection.Right -> offset.animateTo(offset(x = endX), animationSpec)
            SwipingDirection.Up -> offset.animateTo(offset(y = -endY), animationSpec)
            SwipingDirection.Down -> offset.animateTo(offset(y = endY), animationSpec)
        }
        this.swipedDirection = direction
    }

    private fun offset(x: Float = offset.value.x, y: Float = offset.value.y): Offset {
        return Offset(x, y)
    }

    internal suspend fun drag(x: Float, y: Float) {
        offset.animateTo(offset(x, y))
    }
}