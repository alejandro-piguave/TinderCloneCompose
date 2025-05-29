package com.apiguave.core_ui.components

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
import com.apiguave.core_ui.theme.Orange
import com.apiguave.core_ui.theme.Pink


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