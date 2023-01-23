package com.apiguave.tinderclonecompose.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.apiguave.tinderclonecompose.extensions.withLinearGradient

@Composable
fun RoundGradientButton(@DrawableRes resId: Int, color1: Color, color2: Color, onClick: (() -> Unit)){
    RoundGradientButton(painter = painterResource(resId), color1 = color1, color2 = color2, onClick = onClick)
}

@Composable
fun RoundGradientButton(imageVector: ImageVector, color1: Color, color2: Color, onClick: (() -> Unit)){
    RoundGradientButton(painter = rememberVectorPainter(image = imageVector), color1 = color1, color2 = color2, onClick = onClick)
}

@Composable
fun RoundGradientButton(painter: Painter, color1: Color, color2: Color, onClick: (() -> Unit)){
    IconButton(onClick = onClick) {
        Icon(
            painter = painter,
            modifier = Modifier
                .border(
                    4.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(
                            color1,
                            color2
                        )
                    ), shape = CircleShape
                )
                .padding(12.dp)
                .size(44.dp)
                .withLinearGradient(color1, color2)
            , contentDescription = null
        )
    }
}
