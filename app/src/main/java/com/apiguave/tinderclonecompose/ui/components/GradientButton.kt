package com.apiguave.tinderclonecompose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.apiguave.tinderclonecompose.ui.theme.Orange
import com.apiguave.tinderclonecompose.ui.theme.Pink


@Composable
fun GradientButton(modifier: Modifier = Modifier, enabled: Boolean = true, onClick: () -> Unit, content: @Composable RowScope.() -> Unit) {
    Button(
        contentPadding = PaddingValues(),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent, contentColor = MaterialTheme.colors.onPrimary),
        onClick = onClick
    ) {
            Row(
                modifier = modifier
                    .alpha(if (enabled) 1f else .12f)
                    .background(Brush.horizontalGradient(listOf(Pink, Orange)))
                    .padding(ButtonDefaults.ContentPadding)
                    .width(IntrinsicSize.Max)
                ,
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                content = content
            )
    }
}

/*
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GradientButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource1 = remember { MutableInteractionSource1() },
    elevation: ButtonElevation? = ButtonDefaults.elevation(),
    shape: Shape = MaterialTheme.shapes.small,
    border: BorderStroke? = null,
    contentColor: Color = MaterialTheme.colors.onPrimary,
    disabledContentColor: Color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled),
    disabledBackgroundColor: Color = MaterialTheme.colors.onSurface.copy(alpha = 0.12f)
        .compositeOver(MaterialTheme.colors.surface),
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit
) {
    val actualElevation = elevation?.elevation(enabled, interactionSource)?.value ?: 0.dp
    val actualContentColor = if (enabled) contentColor else disabledContentColor
    val background = if (enabled) {
        Modifier.background(brush = Brush.horizontalGradient(listOf(Pink, Orange)))
    } else {
        Modifier.background(color = disabledBackgroundColor)
    }

    Surface(
        onClick = onClick,
        modifier = modifier
            .shadow(actualElevation, shape, true)
            .then(background),
        enabled = enabled,
        shape = shape,
        color = Color.Transparent,
        contentColor = actualContentColor.copy(alpha = 1f),
        border = border,
        elevation = 0.dp,
        interactionSource = interactionSource
    ) {
        CompositionLocalProvider(LocalContentAlpha provides actualContentColor.alpha) {
            ProvideTextStyle(
                value = MaterialTheme.typography.button
            ) {
                Row(
                    Modifier
                        .defaultMinSize(
                            minWidth = ButtonDefaults.MinWidth,
                            minHeight = ButtonDefaults.MinHeight
                        )
                        .padding(contentPadding),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    content = content
                )
            }
        }
    }
}

*/