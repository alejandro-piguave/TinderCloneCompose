package com.apiguave.core_ui.components

import androidx.annotation.ArrayRes
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.apiguave.core_ui.theme.Orange
import com.apiguave.core_ui.theme.Pink
import com.apiguave.core_ui.theme.SystemGray4

@Composable
fun HorizontalPicker(@ArrayRes id: Int, selectedIndex: Int, onOptionClick: (Int) -> Unit) {
    val options = stringArrayResource(id = id)
    val offsetX = remember { Animatable(0f) }

    val density = LocalDensity.current
    var itemWidth by remember { mutableStateOf(0f) }
    var itemHeight by remember { mutableStateOf(0f) }

    LaunchedEffect(key1 = selectedIndex) {
        if(selectedIndex < 0){
            offsetX.snapTo(itemWidth * selectedIndex)
        } else {
            offsetX.animateTo(itemWidth * selectedIndex)
        }
    }

    Box {
        Row(
            modifier =
            Modifier
                .fillMaxWidth()
                .border(
                    BorderStroke(
                        1.dp,
                        if (isSystemInDarkTheme()) Color.DarkGray else SystemGray4
                    )
                )
                .onGloballyPositioned {
                    itemWidth = density.run { (it.size.width / options.size).toDp().value }
                    itemHeight = density.run { it.size.height.toDp() }.value

                }
        ) {
            options.forEachIndexed { index, s ->
                OptionButton(
                    modifier = Modifier.weight(1.0f),
                    text = s,
                    onClick = {
                        onOptionClick(index)
                    })
            }
        }

        if(selectedIndex >= 0){
            Surface(
                elevation = 2.dp, modifier = Modifier
                    .offset(x = offsetX.value.dp)
                    .size(width = itemWidth.dp, height = itemHeight.dp)
                    .padding(4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(Brush.horizontalGradient(listOf(Pink, Orange)))
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        text = options[selectedIndex],
                    )
                }
            }
        }
    }
}